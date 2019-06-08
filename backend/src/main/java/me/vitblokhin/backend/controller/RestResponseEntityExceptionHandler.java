package me.vitblokhin.backend.controller;

import me.vitblokhin.backend.dto.ExceptionDto;
import me.vitblokhin.backend.exception.ItemAlreadyExistsException;
import me.vitblokhin.backend.exception.ItemNotFoundException;
import me.vitblokhin.backend.exception.ServerException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<Object> notFoundHandler(ItemNotFoundException ex, final WebRequest request) {
        return handleExceptionInternal(ex,
                new ExceptionDto(ex.getMessage()),
                new HttpHeaders(),
                HttpStatus.NOT_FOUND,
                request);
    }

    @ExceptionHandler(ItemAlreadyExistsException.class)
    public ResponseEntity<Object> alreadyExistsHandler(ItemAlreadyExistsException ex, final WebRequest request) {
        return handleExceptionInternal(ex,
                new ExceptionDto(ex.getMessage()),
                new HttpHeaders(),
                HttpStatus.CONFLICT,
                request);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> authorizeErrorHandler(BadCredentialsException ex, final WebRequest request) {
        return handleExceptionInternal(ex,
                new ExceptionDto(ex.getMessage()),
                new HttpHeaders(),
                HttpStatus.UNAUTHORIZED,
                request);
    }

    @ExceptionHandler({Exception.class, ServerException.class})
    public ResponseEntity<Object> errorHandler(Exception ex, final WebRequest request) {
        return handleExceptionInternal(ex,
                new ExceptionDto(ex.getMessage()),
                new HttpHeaders(),
                HttpStatus.INTERNAL_SERVER_ERROR,
                request);
    }
} // class RestResponseEntityExceptionHandler
