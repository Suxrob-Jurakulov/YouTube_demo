package com.company.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordDTO {
    private String oldPassword;
    private String newPassword;
}
