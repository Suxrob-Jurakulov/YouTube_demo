package com.company.dto.comment;

import com.company.dto.profile.ProfileDTO;
import com.company.dto.video.VideoDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentProfileDTO {
    private Integer id;
    private String content;
    private LocalDateTime createdDate;
    private Integer likeCount;
    private Integer disLikeCount;
    private VideoDTO video;
    private ProfileDTO profile;

}
