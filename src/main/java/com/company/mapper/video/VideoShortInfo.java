package com.company.mapper.video;

import java.time.LocalDateTime;

public interface VideoShortInfo {
    String getId();
    String getName();
    String getPreviewId();
    LocalDateTime getCreatedDate();
    Integer getViewCount();
    Long getTime();

    String getChannelId();
    String getChannelName();
    String getChannelPhotoId();
}
