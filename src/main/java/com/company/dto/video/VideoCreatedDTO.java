package com.company.dto.video;

import com.company.enums.VisibleStatus;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class VideoCreatedDTO {

    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotBlank
    private String attach;
    @NotNull
    private VisibleStatus status;

    private String preview;
    @NotBlank
    private String channelId;

    private String playlist;

    private List<String> tags;
    private Integer categoryId;

}
