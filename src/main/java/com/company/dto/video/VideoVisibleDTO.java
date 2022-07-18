package com.company.dto.video;

import com.company.enums.VisibleStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VideoVisibleDTO {
    private String id;
    private VisibleStatus visibleStatus;
}
