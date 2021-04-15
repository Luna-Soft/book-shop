package com.zutode.bookshopclone.shop.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zutode.bookshopclone.auth.application.dto.id.UserIdDto;
import com.zutode.bookshopclone.shop.application.dto.id.BookIdDto;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class BookRentalWriteDto {

    @JsonProperty("id")
    private Long id;
    @NotNull
    @JsonProperty("user_id")
    private UserIdDto userId;
    @NotNull
    @JsonProperty("book_id")
    private BookIdDto bookId;


}
