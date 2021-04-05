package com.zutode.bookshopclone.auth.application.dto;

import lombok.Data;

@Data
public class UserDetailedDto {

    private Long id;
    private String username;
    private String email;
    private String password;

}
