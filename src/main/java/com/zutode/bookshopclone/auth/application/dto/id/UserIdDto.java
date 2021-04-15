package com.zutode.bookshopclone.auth.application.dto.id;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserIdDto {
    @NotNull
    private Long id;
}
