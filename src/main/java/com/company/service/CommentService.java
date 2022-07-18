package com.company.service;

import com.company.dto.CommentDTO;
import com.company.dto.CommentUpdateDTO;
import com.company.dto.comment.CommentProfileDTO;
import com.company.dto.profile.ProfileDTO;
import com.company.dto.video.VideoDTO;
import com.company.entity.CommentEntity;
import com.company.entity.ProfileEntity;
import com.company.entity.VideoEntity;
import com.company.enums.ProfileRole;
import com.company.exp.BadRequestException;
import com.company.exp.ItemNotFoundException;
import com.company.mapper.comment.CommentInfo;
import com.company.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ProfileService profileService;
    @Autowired
    private VideoService videoService;

    public CommentDTO create(CommentDTO dto) {
        VideoEntity video = videoService.get(dto.getVideoId());
        ProfileEntity currentUser = profileService.getCurrentUser();
        CommentEntity entity = new CommentEntity();
        entity.setVisible(true);
        entity.setProfileId(currentUser.getId());
        entity.setContent(dto.getContent());
        entity.setVideoId(video.getId());
        if (dto.getComment() != null) {
            entity.setCommentId(dto.getComment().getId());
        }
        commentRepository.save(entity);

        return getDto(entity, currentUser);
    }

    private CommentDTO getDto(CommentEntity entity, ProfileEntity user) {
        CommentDTO dto = new CommentDTO();
        dto.setId(entity.getId());
        dto.setContent(entity.getContent());
        dto.setVideoId(entity.getVideoId());

        ProfileDTO profile = new ProfileDTO();
        profile.setId(user.getId());
        profile.setName(user.getName());
        profile.setEmail(user.getEmail());

        if (user.getPhoto() != null) {
            profile.setPhotoUrl(user.getPhoto().getPath());
        }
        dto.setProfile(profile);

        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public void update(CommentUpdateDTO dto) {
        CommentEntity entity = check(dto.getCommentId());
        entity.setContent(dto.getContent());
        commentRepository.save(entity);
    }

    public CommentEntity get(Integer id) {
        return commentRepository.findById(id).orElseThrow(() -> {
            throw new ItemNotFoundException("Comment not found!");
        });
    }

    public void delete(Integer id) {
        CommentEntity entity = get(id);
        ProfileEntity currentUser = profileService.getCurrentUser();
        if (!currentUser.getRole().equals(ProfileRole.ROLE_ADMIN)) {
            if (!entity.getProfileId().equals(currentUser.getId())) {
                throw new BadRequestException("Not access");
            }
        }
        entity.setVisible(false);
        commentRepository.save(entity);
    }

    private CommentEntity check(Integer id) {
        CommentEntity entity = get(id);
        ProfileEntity currentUser = profileService.getCurrentUser();
        if (!entity.getProfileId().equals(currentUser.getId())) {
            throw new BadRequestException("Not access");
        }
        return entity;
    }

    public PageImpl<CommentDTO> list(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CommentEntity> all = commentRepository.findAll(pageable);
        List<CommentEntity> list = all.getContent();
        List<CommentDTO> dtoList = new LinkedList<>();
        list.forEach(entity -> {
            dtoList.add(getDto(entity, profileService.get(entity.getProfileId())));
        });
        return new PageImpl<>(dtoList, pageable, all.getTotalElements());
    }

    public List<CommentProfileDTO> listByProfileId(Integer profileId) {
        profileService.get(profileId);
        return getCommentDTO(commentRepository.getCommentListByProfileId(profileId));
    }

    public List<CommentProfileDTO> listByProfile() {
        ProfileEntity currentUser = profileService.getCurrentUser();
        return getCommentDTO(commentRepository.getCommentListByProfileId(currentUser.getId()));
    }

    public List<CommentProfileDTO> listByVideoId(String videoId) {
        videoService.get(videoId);
        return getCommentInfoDTO(commentRepository.getListByVideoId(videoId));
    }

    public List<CommentProfileDTO> listByReplyCommentId(Integer commentId) {
        get(commentId);
        return getCommentInfoDTO(commentRepository.getListByCommentId(commentId));
    }

    private List<CommentProfileDTO> getCommentDTO(List<CommentInfo> list){
        List<CommentProfileDTO> dtoList = new LinkedList<>();
        list.forEach(entity -> {
            CommentProfileDTO dto = new CommentProfileDTO();
            dto.setId(entity.getId());
            dto.setContent(entity.getContent());
            dto.setCreatedDate(entity.getCreatedDate());
            dto.setLikeCount(entity.getLikeCount());
            dto.setDisLikeCount(entity.getDisLikeCount());
            VideoDTO video = new VideoDTO();
            video.setId(entity.getVideoId());
            video.setName(entity.getVideoName());
            video.setDuration(entity.getVideoDuration());
            video.setPreviewAttachId(entity.getVideoPreviewId());
            dto.setVideo(video);
            dtoList.add(dto);
        });
        return dtoList;
    }

    private List<CommentProfileDTO> getCommentInfoDTO(List<CommentInfo> list) {
        List<CommentProfileDTO> dtoList = new LinkedList<>();
        list.forEach(entity -> {
            CommentProfileDTO dto = new CommentProfileDTO();
            dto.setId(entity.getId());
            dto.setContent(entity.getContent());
            dto.setCreatedDate(entity.getCreatedDate());
            dto.setLikeCount(entity.getLikeCount());
            dto.setDisLikeCount(entity.getDisLikeCount());

            ProfileDTO profile = new ProfileDTO();
            profile.setId(entity.getProfileId());
            profile.setName(entity.getProfileName());
            profile.setPhotoId(entity.getProfilePhotoId());
            dto.setProfile(profile);
            dtoList.add(dto);
        });
        return dtoList;
    }
}
