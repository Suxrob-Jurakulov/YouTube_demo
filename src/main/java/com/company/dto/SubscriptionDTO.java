package com.company.dto;

import com.company.dto.channel.ChannelDTO;
import com.company.enums.NotificationType;
import com.company.enums.PositionStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SubscriptionDTO {
    private Integer id;
    @NotNull
    private String channelId;
    private NotificationType type;
    private ChannelDTO channel;
    private PositionStatus status;
    private LocalDateTime createdDate;
}
