package com.company.dto.video;

import com.company.enums.LikeStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class VideoWatchedDTO {

    private String videoId;
    private LikeStatus status;

}
