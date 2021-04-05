package com.zutode.bookshopclone.shop.application.exception;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class ErrorWrapper {

    private final LocalDateTime timestamp;
    private final int status;
    private final String error;
    private final String message;

}
