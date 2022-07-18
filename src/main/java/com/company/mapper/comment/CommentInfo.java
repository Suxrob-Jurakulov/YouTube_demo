package com.company.mapper.comment;

import java.time.LocalDateTime;

public interface CommentInfo {
    Integer getId();
    String getContent();
    LocalDateTime getCreatedDate();

    Integer getLikeCount();
    Integer getDisLikeCount();

    String getVideoId();
    String getVideoName();
    String getVideoPreviewId();
    String getVideoDuration();

    Integer getProfileId();
    String getProfileName();
    String getProfilePhotoId();

}
