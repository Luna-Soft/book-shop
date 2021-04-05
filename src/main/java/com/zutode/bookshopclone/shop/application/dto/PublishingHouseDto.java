package com.zutode.bookshopclone.shop.application.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PublishingHouseDto {


    private Long id;
    @NotBlank
    private String name;
}
