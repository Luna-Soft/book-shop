package com.zutode.bookshopclone.auth.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SignUpRequestDto {

    private String username;
    private String email;
    private String password;
    @JsonProperty("confirm_password")
    private String confirmPassword;
}
