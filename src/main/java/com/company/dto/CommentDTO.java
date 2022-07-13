package com.company.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentDTO {
    private Integer id;
    private String content;
    private String videoId;

    private ProfileDTO profile;
    private LocalDateTime createdDate;

}
