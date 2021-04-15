package com.zutode.bookshopclone.shop.application.exception;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import javax.swing.text.html.parser.Entity;
import java.time.LocalDateTime;

@ControllerAdvice
public class RestApiExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(value = InvalidInputParameterException.class)
    protected ResponseEntity<Object> handle(InvalidInputParameterException ex, WebRequest request) {
        return createResponseEntity(ex, request, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = DomainValidationException.class)
    protected ResponseEntity<Object> handle(DomainValidationException ex, WebRequest request) {
        return createResponseEntity(ex, request, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = EmptyResultDataAccessException.class)
    protected ResponseEntity<Object> handle(EmptyResultDataAccessException ex, WebRequest request) {
        return createResponseEntity(ex, request, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = ResourceNotFoundException.class)
    protected ResponseEntity<Object> handle(ResourceNotFoundException ex, WebRequest request) {
        return createResponseEntity(ex, request, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = ApplicationValidationException.class)
    protected ResponseEntity<Object> handle(ApplicationValidationException ex, WebRequest request) {
        return createResponseEntity(ex, request, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(value = ResourceAlreadyExistsException.class)
    protected ResponseEntity<Object> handle(ResourceAlreadyExistsException ex, WebRequest request) {
        return createResponseEntity(ex, request, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = NoAuthenticationException.class)
    protected ResponseEntity<Object> handle(NoAuthenticationException ex, WebRequest request) {
        return createResponseEntity(ex, request, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = ExpiredJwtException.class)
    protected ResponseEntity<Object> handle(ExpiredJwtException ex, WebRequest request) {
        return createResponseEntity(ex, request, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = IllegalStateException.class)
    protected ResponseEntity<Object> handle(IllegalStateException ex, WebRequest request) {
        return createResponseEntity(ex, request, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = EntityNotFoundException.class)
    protected ResponseEntity<Object> handle(EntityNotFoundException ex, WebRequest request) {
        return createResponseEntity(ex, request, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = DataIntegrityViolationException.class)
    protected ResponseEntity<Object> handle(DataIntegrityViolationException ex, WebRequest request) {
        return createResponseEntity(ex, request, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    protected ResponseEntity<Object> handle(AccessDeniedException ex, WebRequest request) {
        return createResponseEntity(ex, request, HttpStatus.FORBIDDEN);
    }


    private ResponseEntity<Object> createResponseEntity(RuntimeException ex, WebRequest request, HttpStatus status) {
        ErrorWrapper errorWrapper = ErrorWrapper.builder()
                .error(status.getReasonPhrase())
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .build();
        return handleExceptionInternal(ex, errorWrapper, new HttpHeaders(), status, request);
    }

}
