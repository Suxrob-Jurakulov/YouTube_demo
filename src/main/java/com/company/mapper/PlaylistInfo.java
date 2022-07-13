package com.company.mapper;

import com.company.entity.ChannelEntity;
import com.company.entity.ProfileEntity;
import com.company.enums.VisibleStatus;

public interface PlaylistInfo {
    String getId();
    String getName();
    String getDescription();
    VisibleStatus getStatus();
    Integer getOrderNum();
    ChannelEntity getChannel();
    ProfileEntity getProfile();
}
