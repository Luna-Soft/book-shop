package com.zutode.bookshopclone.shop.application.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AuthorDto {

    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String surname;


}
