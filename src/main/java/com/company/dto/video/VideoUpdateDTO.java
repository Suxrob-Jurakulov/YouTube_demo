package com.company.dto.video;

import com.company.enums.PositionStatus;
import com.company.enums.VisibleStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VideoUpdateDTO {

    private String name;
    private String description;
    private String reviewId;

}
