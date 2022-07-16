package com.company.dto;

import com.company.dto.channel.ChannelDTO;
import com.company.enums.VisibleStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlaylistDTO {
    private String id;
    private String name;
    private String description;
    private VisibleStatus status;
    private Integer orderNum;
    private String channel;
    private ChannelDTO channelDTO;
    private String review;
}
