package com.company.service;

import com.company.dto.CategoryDTO;
import com.company.dto.attach.AttachDTO;
import com.company.dto.channel.ChannelDTO;
import com.company.dto.playlist.PlaylistDTO;
import com.company.dto.profile.ProfileDTO;
import com.company.dto.profile.ResponseInfoDTO;
import com.company.dto.tag.TagDTO;
import com.company.dto.tag.TagVideoDTO;
import com.company.dto.video.*;
import com.company.entity.*;
import com.company.enums.PositionStatus;
import com.company.enums.ProfileRole;
import com.company.enums.VisibleStatus;
import com.company.exp.ItemNotFoundException;
import com.company.mapper.video.VideoFullInfo;
import com.company.mapper.video.VideoShortInfo;
import com.company.repository.VideoRepository;
import com.company.repository.VideoTagRepository;
import com.company.repository.WatchedVideoRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class VideoService {
    @Value("${attach.folder}")
    private String attachFolder;
    @Value("${server.url}")
    private String serverUrl;

    @Autowired
    private VideoRepository videoRepository;
    @Autowired
    private AttachService attachService;
    @Autowired
    private ProfileService profileService;
    @Autowired
    private WatchedVideoRepository watchedVideoRepository;
    @Autowired
    private VideoTagRepository videoTagRepository;
    @Autowired
    private ChannelService channelService;
    @Autowired
    private PlaylistService playlistService;
    @Autowired
    private TagService tagService;

    @SneakyThrows
    public ResponseInfoDTO create(VideoCreatedDTO dto) {

        VideoEntity video = new VideoEntity();
        video.setName(dto.getName());
        video.setDescription(dto.getDescription());
        video.setStatus(dto.getStatus());
        video.setChannelId(dto.getChannelId());
        video.setAttachId(dto.getAttach());
        video.setVisible(true);
        video.setCategoryId(dto.getCategoryId());
        video.setPreviewId(dto.getPreview());
        AttachEntity attach = attachService.get(dto.getAttach());
        video.setTime(attach.getDuration());
        videoRepository.save(video);

        ResponseInfoDTO responseInfoDTO = new ResponseInfoDTO();
        responseInfoDTO.setStatus(1);
        responseInfoDTO.setMessage("Created");
        return responseInfoDTO;
    }

    public ResponseInfoDTO update(VideoUpdateDTO dto, String videoId) {
        ResponseInfoDTO responseInfoDTO = checkProfile(videoId);
        if (responseInfoDTO.getStatus() == 1) {
            VideoEntity entity = get(videoId);
            entity.setDescription(dto.getDescription());
            entity.setName(dto.getName());

            AttachEntity oldPhoto = entity.getPreview();
            entity.setPreviewId(dto.getReviewId());
            videoRepository.save(entity);
            if (oldPhoto != null) {
                attachService.delete(oldPhoto.getId());
            }
        }
        return responseInfoDTO;
    }

    public ResponseInfoDTO updateStatus(VideoVisibleDTO dto) {
        ResponseInfoDTO responseInfoDTO = checkProfile(dto.getId());
        if (responseInfoDTO.getStatus() == 1) {
            videoRepository.changeStatus(dto.getId(), dto.getVisibleStatus());
        }
        return responseInfoDTO;
    }

    public void increaseViewCountAndLike(VideoWatchedDTO dto) {
        VideoEntity video = get(dto.getVideoId());
        WatchedVideoEntity watched = new WatchedVideoEntity();
        watched.setVideoId(dto.getVideoId());
        watched.setProfileId(profileService.getCurrentUser().getId());
        watched.setStatus(dto.getStatus());
        watchedVideoRepository.save(watched);
        videoRepository.increaseCount(video.getId());
    }

    public List<VideoShortInfoDTO> getByCategory(Integer categoryId, Integer page, Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "created_date");
        Pageable pageable = PageRequest.of(page, size, sort);

        return videoRepository.getByCategory(categoryId, pageable).stream().map(entity -> {
            VideoShortInfoDTO dto = new VideoShortInfoDTO();
            dto.setVideoId(entity.getId());
            dto.setVideoName(entity.getName());
            dto.setViewCount(entity.getViewCount());
            dto.setVideoAttachId(entity.getPreviewId());
            dto.setVideoAttachUrl(attachService.getFullUrl(entity.getPreviewId()));
            dto.setDuration(getDurationFromLong(entity.getTime()));
            ChannelDTO channel = new ChannelDTO();
            channel.setId(entity.getChannelId());
            channel.setName(entity.getChannelName());
            AttachDTO attachDTO = new AttachDTO();
            attachDTO.setId(entity.getChannelPhotoId());
            attachDTO.setUrl(attachService.getFullUrl(entity.getChannelPhotoId()));
            channel.setPhoto(attachDTO);
            dto.setChannel(channel);
            return dto;
        }).collect(Collectors.toList());
    }

    public List<VideoShortInfoDTO> searchByTagId(Integer tagId, Integer page, Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "created_date");
        Pageable pageable = PageRequest.of(page, size, sort);

        return videoRepository.searchByTagId(tagId, pageable).stream().map(entity -> {
            VideoShortInfoDTO dto = new VideoShortInfoDTO();
            dto.setVideoId(entity.getId());
            dto.setVideoName(entity.getName());
            dto.setViewCount(entity.getViewCount());
            dto.setVideoAttachId(entity.getPreviewId());
            dto.setVideoAttachUrl(attachService.getFullUrl(entity.getPreviewId()));
            dto.setDuration(getDurationFromLong(entity.getTime()));
            ChannelDTO channel = new ChannelDTO();
            channel.setId(entity.getChannelId());
            channel.setName(entity.getChannelName());
            AttachDTO attachDTO = new AttachDTO();
            attachDTO.setId(entity.getChannelPhotoId());
            attachDTO.setUrl(attachService.getFullUrl(entity.getChannelPhotoId()));
            channel.setPhoto(attachDTO);
            dto.setChannel(channel);
            return dto;
        }).collect(Collectors.toList());
    }

    public List<VideoShortInfoDTO> searchByTitle(String title) {

        return videoRepository.searchByTitle(title).stream().map(entity -> {
            VideoShortInfoDTO dto = new VideoShortInfoDTO();
            dto.setVideoId(entity.getId());
            dto.setVideoName(entity.getName());
            dto.setViewCount(entity.getViewCount());
            dto.setVideoAttachId(entity.getPreviewId());
            dto.setVideoAttachUrl(attachService.getFullUrl(entity.getPreviewId()));
            dto.setDuration(getDurationFromLong(entity.getTime()));
            ChannelDTO channel = new ChannelDTO();
            channel.setId(entity.getChannelId());
            channel.setName(entity.getChannelName());
            AttachDTO attachDTO = new AttachDTO();
            attachDTO.setId(entity.getChannelPhotoId());
            attachDTO.setUrl(attachService.getFullUrl(entity.getChannelPhotoId()));
            channel.setPhoto(attachDTO);
            dto.setChannel(channel);
            return dto;
        }).collect(Collectors.toList());
    }

    public String getDurationFromLong(Long ms) {
        return String.format("%02d:%02d", TimeUnit.SECONDS.toHours(ms),
                TimeUnit.SECONDS.toMinutes(ms) - TimeUnit.HOURS.toMinutes(TimeUnit.SECONDS.toHours(ms)));
    }

    public VideoFullInfoDTO getVideoById(String id) {
        ProfileEntity currentUser = profileService.getCurrentUser();
        VideoFullInfo entity = videoRepository.getVideoById(id, currentUser.getId());

        if (entity.getVisibleStatus().equals(VisibleStatus.PRIVATE)) {
            if (!currentUser.getRole().equals(ProfileRole.ROLE_ADMIN)) {
                if (!entity.getChannelProfileId().equals(currentUser.getId())) {
                    throw new ItemNotFoundException("Not found");
                }
            }
        }
        VideoFullInfoDTO dto = new VideoFullInfoDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setViewCount(entity.getViewCount());
        dto.setShareCount(entity.getSharedCount());
        dto.setLikeCount(entity.getLikeCount());
        dto.setDislikeCount(entity.getLikeCount());
        dto.setStatus(entity.getLikeStatus());

        AttachDTO attach = new AttachDTO();
        attach.setId(entity.getAttachId());
        attach.setUrl(attachService.getFullUrl(entity.getAttachId()));
        attach.setDuration(getDurationFromLong(entity.getTime()));
        dto.setAttach(attach);

        AttachDTO preview = new AttachDTO();
        preview.setId(entity.getPreviewId());
        preview.setUrl(attachService.getFullUrl(entity.getPreviewId()));
        dto.setPreview(preview);

        CategoryDTO category = new CategoryDTO();
        category.setId(entity.getCategoryId());
        category.setName(entity.getCategoryName());
        dto.setCategory(category);

        ChannelDTO channel = new ChannelDTO();
        channel.setId(entity.getChannelId());
        channel.setName(entity.getChannelName());
        channel.setPhoto(new AttachDTO(attachService.getFullUrl(entity.getChannelPhotoId())));
        dto.setChannel(channel);

        List<VideoTagEntity> tagList = videoTagRepository.getAllTagNameByVideoId(entity.getId());
        List<TagDTO> tagDTOS = new LinkedList<>();
        tagList.forEach(tagVideo -> {
            TagDTO tag = new TagDTO();
            tag.setId(tagVideo.getTagId());
            tag.setName(tagVideo.getTag().getName());
            tagDTOS.add(tag);
        });
        dto.setTags(tagDTOS);
        return dto;
    }

    public VideoEntity get(String id) {
        return videoRepository.findById(id).orElseThrow(() -> {
            throw new ItemNotFoundException("Video not found");
        });
    }

    private ResponseInfoDTO checkProfile(String videoId) {
        ProfileEntity profile = profileService.getCurrentUser();
        VideoEntity video = get(videoId);
        if (!video.getChannel().getProfileId().equals(profile.getId()) &&
                !profile.getRole().equals(ProfileRole.ROLE_ADMIN)) {
            return new ResponseInfoDTO(-1, "No access");
        }
        return new ResponseInfoDTO(1, "Success");
    }

    public List<VideoShortOwnerPlaylistDTO> getVideoListPagination(Integer page, Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "created_date");
        Pageable pageable = PageRequest.of(page, size, sort);

        List<VideoShortInfo> videoShortInfos = videoRepository.getVideoPagination(pageable);
        List<VideoShortOwnerPlaylistDTO> list = new LinkedList<>();

        videoShortInfos.forEach(entity -> {
            VideoShortOwnerPlaylistDTO dto = new VideoShortOwnerPlaylistDTO();
            dto.setVideoId(entity.getId());
            dto.setVideoName(entity.getName());
            dto.setViewCount(entity.getViewCount());
            dto.setDuration(getDurationFromLong(entity.getTime()));
            dto.setPreviewAttachId(entity.getPreviewId());
            dto.setPreviewAttachUrl(attachService.getFullUrl(entity.getPreviewId()));

            ChannelDTO channel = new ChannelDTO();
            channel.setId(entity.getChannelId());
            channel.setName(entity.getChannelName());
            channel.setPhoto(new AttachDTO(attachService.getFullUrl(entity.getChannelPhotoId())));
            dto.setChannel(channel);

            ChannelEntity channelEntity = channelService.get(entity.getChannelId());
            Integer profileId = channelEntity.getProfileId();
            String name = channelEntity.getProfile().getName();

            dto.setProfile(new ProfileDTO(profileId, name));
            PlaylistEntity playlist = playlistService.getPlaylistByVideoId(entity.getId());

            dto.setPlaylist(new PlaylistDTO(playlist.getId(), playlist.getName()));

            list.add(dto);
        });
        return list;
    }

    public List<VideoDTO> getVideoListByChannelPagination(String channelId, int page, int size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "created_date");
        Pageable pageable = PageRequest.of(page, size, sort);
        List<VideoDTO> list = new LinkedList<>();
        videoRepository.getList(channelId, pageable).forEach(entity -> {
            VideoDTO dto = new VideoDTO();
            dto.setId(entity.getId());
            dto.setName(entity.getName());
            dto.setViewCount(entity.getViewCount());
            dto.setCreatedDate(entity.getCreatedDate());
            dto.setDuration(getDurationFromLong(entity.getTime()));
            AttachDTO attachDTO = new AttachDTO();
            attachDTO.setId(entity.getAttachId());
            attachDTO.setUrl(attachService.getFullUrl(entity.getAttachId()));
            dto.setReview(attachDTO);
            list.add(dto);
        });
        return list;
    }

    public void addTag(VideoTagDTO dto) {
        VideoEntity video = get(dto.getVideoId());
        TagEntity tag = tagService.get(dto.getTagId());

        VideoTagEntity entity = new VideoTagEntity();
        entity.setTagId(tag.getId());
        entity.setVideoId(video.getId());
        videoTagRepository.save(entity);
    }

    public void delete(VideoTagDTO dto) {
        VideoEntity video = get(dto.getVideoId());
        TagEntity tag = tagService.get(dto.getTagId());
        videoTagRepository.deleteTag(video.getId(), tag.getId());
    }

    public List<TagVideoDTO> getTagListByVideoId(String videoId) {
            get(videoId);
       List<VideoTagEntity> entityList = videoTagRepository.findByVideoIdAndVisibleTrue(videoId);
       List<TagVideoDTO> list = new LinkedList<>();
       entityList.forEach(entity -> {
           TagVideoDTO dto = new TagVideoDTO();
           dto.setId(entity.getId());
           dto.setVideoId(entity.getVideoId());
           dto.setCreatedDate(entity.getCreatedDate());
           dto.setTag(new TagDTO(entity.getTag().getId(), entity.getTag().getName()));
           list.add(dto);
       });
       return list;
    }
}
