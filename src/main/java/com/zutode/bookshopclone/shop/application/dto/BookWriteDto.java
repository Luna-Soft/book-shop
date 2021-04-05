package com.zutode.bookshopclone.shop.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
public class BookWriteDto {


    private Long id;
    @NotBlank
    private String title;
    @NotBlank
    @JsonProperty("ISBN")
    private String ISBN;
    @NotEmpty
    private Set<Long> genres;
    @NotEmpty
    private Set<Long> authors;
    @NotNull
    @JsonProperty("publishing_house")
    private Long publishingHouse;
    private String description;
    @NotNull
    private Long price;


  /*

  @Data
    public static class IdDto {
        @NotNull
        private Long id;
    }

   */


}
