package com.company.dto;

import com.company.enums.ProfileRole;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileDTO {
    private Integer id;
    private String name;
    private String email;
    private ProfileRole role;
    private String password;
    private String photoId;
    private String photoUrl;
    private AttachDTO photo;

    private String jwt;
}
