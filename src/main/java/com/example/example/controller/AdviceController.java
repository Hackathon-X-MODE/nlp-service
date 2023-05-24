package com.example.example.controller;

import com.example.example.exception.EntityNotFoundException;
import com.example.example.model.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AdviceController {

    @ExceptionHandler({IllegalArgumentException.class, RuntimeException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto badRequest(Exception e) {
        return ErrorDto.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message(e.getMessage())
                .build();
    }

    @ExceptionHandler({EntityNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDto notFound(Exception e) {
        return ErrorDto.builder()
                .code(HttpStatus.NOT_FOUND.value())
                .message(e.getMessage())
                .build();
    }
}
