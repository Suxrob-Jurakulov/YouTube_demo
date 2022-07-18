package com.company.dto;

import com.company.dto.profile.ProfileDTO;
import com.company.enums.ReportType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReportDTO {
    private Integer id;
    private Integer profileId;
    @NotBlank
    private String content;
    @NotNull
    private String joinId;
    @NotNull
    private ReportType type;
    private ProfileDTO profile;
}
