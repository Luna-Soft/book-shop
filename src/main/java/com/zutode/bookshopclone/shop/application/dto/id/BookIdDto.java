package com.zutode.bookshopclone.shop.application.dto.id;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class BookIdDto {
    @NotNull
    private Long id;
}
