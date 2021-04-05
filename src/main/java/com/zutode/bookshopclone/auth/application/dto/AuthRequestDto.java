package com.zutode.bookshopclone.auth.application.dto;

import lombok.Data;

@Data
public class AuthRequestDto {

    private String username;
    private String password;
}
