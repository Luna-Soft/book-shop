package com.zutode.bookshopclone.shop.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class BookReadDto {


    private Long id;
    @NotBlank
    private String title;
    @JsonProperty("ISBN")
    @NotBlank
    private String ISBN;
    private String description;
    @NotNull
    private Long price;


}
