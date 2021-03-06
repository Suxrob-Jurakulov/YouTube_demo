package com.company.dto.profile;

import com.company.dto.attach.AttachDTO;
import com.company.enums.ProfileRole;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileDTO {
    private Integer id;
    private String name;
    private String email;
    private String password;
    private ProfileRole role;
    private String photoId;
    private String photoUrl;
    private AttachDTO photo;

    private String jwt;

    public ProfileDTO(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public ProfileDTO(Integer id, String name, String email, String photoUrl) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.photoUrl = photoUrl;
    }
}
