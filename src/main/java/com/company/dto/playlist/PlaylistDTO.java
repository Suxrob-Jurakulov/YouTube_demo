package com.company.dto.playlist;

import com.company.dto.channel.ChannelDTO;
import com.company.enums.VisibleStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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

    public PlaylistDTO(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
