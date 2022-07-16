package com.company.mapper;

import com.company.enums.Status;
import com.company.enums.VisibleStatus;

public interface PlaylistInfo {

    String getId();
    String getName();
    String getDescription();
    VisibleStatus getStatus();
    Integer getOrders();

    String getChannelId();
    String getChannelName();
    String getChannelAttachId();

    Integer getProfileId();
    String getProfileName();
    String getProfileAttachId();

}

//id,name,description,status(private,public),order_num,

//channel(id,name,photo(id,url), profile(id,name,surname,photo(id,url)))
