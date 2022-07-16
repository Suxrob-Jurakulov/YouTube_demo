package com.company.dto.playlist;

import com.company.dto.channel.ChannelDTO;
import com.company.entity.ChannelEntity;
import com.company.enums.VisibleStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FullInfoDTO {
    private String id;
    private String name;
    private String description;
    private VisibleStatus status;
    private Integer orderNum;
    private ChannelDTO channel;
}
