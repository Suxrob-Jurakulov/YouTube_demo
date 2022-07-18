package com.company.dto.video;

import com.company.dto.channel.ChannelDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideoShortInfoDTO {
    private String videoId;
    private String videoName;
    private Integer viewCount;
    private String duration;
    private String videoAttachId;
    private String videoAttachUrl;
    private ChannelDTO channel;

}
