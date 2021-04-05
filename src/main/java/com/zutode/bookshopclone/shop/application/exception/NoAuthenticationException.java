package com.zutode.bookshopclone.shop.application.exception;

public class NoAuthenticationException extends RuntimeException {

    public NoAuthenticationException(String message) {
        super(message);
    }
}
