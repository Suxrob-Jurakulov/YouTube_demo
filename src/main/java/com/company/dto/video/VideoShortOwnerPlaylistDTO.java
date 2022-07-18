package com.company.dto.video;

import com.company.dto.channel.ChannelDTO;
import com.company.dto.playlist.PlaylistDTO;
import com.company.dto.profile.ProfileDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VideoShortOwnerPlaylistDTO {
    private String videoId;
    private String videoName;
    private Integer viewCount;
    private String duration;
    private String previewAttachId;
    private String previewAttachUrl;
    private ChannelDTO channel;
    private ProfileDTO profile;
    private PlaylistDTO playlist;
}
