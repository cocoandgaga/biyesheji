package com.zxj.finalexam.dto;

import lombok.Data;

@Data
public class PasswordDTO {
    private Long userId;
    private String oldPassword;
    private String newPassword;
    private String confirmPassword;
}
