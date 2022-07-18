package com.company.dto.video;

import com.company.dto.attach.AttachDTO;
import com.company.dto.channel.ChannelDTO;
import com.company.enums.PositionStatus;
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
    private Integer viewCount;
    private Integer sharedCount;
    private String duration;
    private String attachId;
    private AttachDTO attach;
    private String previewAttachId;
    private AttachDTO review;
    private ChannelDTO channel;
    private LocalDateTime createdDate;
    private Boolean visible;
    private PositionStatus status;

}
