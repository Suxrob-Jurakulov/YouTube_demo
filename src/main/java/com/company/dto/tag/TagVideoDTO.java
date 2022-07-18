package com.company.dto.tag;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TagVideoDTO {
    private Integer id;
    private String videoId;
    private TagDTO tag;
    private LocalDateTime createdDate;
}
