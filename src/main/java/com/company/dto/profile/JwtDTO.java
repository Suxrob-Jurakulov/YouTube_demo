package com.company.dto.profile;

import com.company.enums.ProfileRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtDTO {
    private Integer id;

    public JwtDTO(Integer id) {
        this.id = id;
    }
}
