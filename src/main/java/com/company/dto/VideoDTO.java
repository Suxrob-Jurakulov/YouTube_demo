package com.company.dto;

import com.company.dto.channel.ChannelDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VideoDTO {
    private String id;
    private String name;
    private String description;
    private LocalDateTime createdDate;
    private AttachDTO attach;
    private ChannelDTO channel;
}
