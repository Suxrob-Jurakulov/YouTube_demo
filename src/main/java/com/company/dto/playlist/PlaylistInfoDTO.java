package com.company.dto.playlist;

import com.company.dto.channel.ChannelDTO;
import com.company.entity.ChannelEntity;
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
public class PlaylistInfoDTO {
    private String id;
    private String name;
    private String description;
    private VisibleStatus status;
    private Integer orderNum;
    private ChannelEntity channel;

//    id,name,description,status(private,public),order_num,
//    channel(id,name,photo(id,url), profile(id,name,surname,photo(id,url)))

}
