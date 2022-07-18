package com.company.dto;

import com.company.dto.profile.ProfileDTO;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
public class CommentDTO {
    private Integer id;
    @NotBlank
    private String content;
    @NotNull(message = "VideoId majburiy")
    private String videoId;
    private ProfileDTO profile;
    private LocalDateTime createdDate;
    private LocalDateTime updateDate;
    private CommentDTO comment;

}
