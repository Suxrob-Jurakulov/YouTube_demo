package com.company.dto.attach;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AttachDTO {
    private String id;
    private String originalName;
    private String extension;
    private Long size;
    private LocalDateTime createdDate;
    private String url;
    private String duration;

    public AttachDTO(String url) {
        this.url = url;
    }
}
