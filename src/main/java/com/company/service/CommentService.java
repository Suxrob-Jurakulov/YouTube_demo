package com.company.service;

import com.company.dto.CommentDTO;
import com.company.dto.ProfileDTO;
import com.company.entity.CommentEntity;
import com.company.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    public CommentDTO create(CommentDTO dto) {
        CommentEntity entity = new CommentEntity();
        entity.setVisible(true);
        entity.setProfileId(dto.getProfile().getId());
        entity.setContent(dto.getContent());
        entity.setVideoId(dto.getVideoId());
        if (dto.getComment() != null){
            entity.setCommentId(dto.getComment().getId());
        }
        commentRepository.save(entity);

        return getDto(entity);
    }

    private CommentDTO getDto (CommentEntity entity){
        CommentDTO dto = new CommentDTO();
        dto.setId(entity.getId());
        dto.setContent(entity.getContent());
        dto.setVideoId(entity.getVideoId());

        ProfileDTO profile = new ProfileDTO();
        profile.setId(entity.getProfileId());
        profile.setName(entity.getProfile().getName());
        profile.setEmail(entity.getProfile().getEmail());

        if (entity.getProfile().getPhoto() != null){
            profile.setPhotoUrl(entity.getProfile().getPhoto().getPath());
        }
        dto.setProfile(profile);

        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }
}
