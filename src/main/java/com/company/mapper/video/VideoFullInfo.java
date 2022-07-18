package com.company.mapper.video;

import com.company.entity.ChannelEntity;
import com.company.enums.LikeStatus;
import com.company.enums.PositionStatus;
import com.company.enums.VisibleStatus;

import java.time.LocalDateTime;

public interface VideoFullInfo {
    VisibleStatus getVisibleStatus();
    String getId();
    String getName();
    String getDescription();
    String getPreviewId();
    String getAttachId();
    String getChannelId();
    String getChannelName();
    Integer getCategoryId();
    String getCategoryName();
    LocalDateTime getCreatedDate();
    Integer getViewCount();
    Integer getSharedCount();
    Long getTime();
    Integer getLikeCount();
    Integer getDisLikeCount();
    LikeStatus getLikeStatus();
    String getChannelPhotoId();
    Integer getChannelProfileId();
}
