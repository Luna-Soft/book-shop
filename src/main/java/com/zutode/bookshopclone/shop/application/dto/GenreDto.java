package com.zutode.bookshopclone.shop.application.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class GenreDto {

    //@Null(groups = {New.class})
    private Long id;
    @NotBlank
    private String name;

    /*
    @Null(groups = {New.class})
    @NotNull(groups = {Existing.class})
    private Integer version;


    public interface New {}
    public interface Existing {}
     */
}
