package com.javaexpress.loans.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GloablEceptionHandler {

    @RestControllerAdvice
    public class GlobalExceptionHandler {

        @ExceptionHandler(LoanAlreadyExists.class)
        @ResponseStatus(HttpStatus.BAD_REQUEST)
        public ErrorResponseDto handleException(LoanAlreadyExists ex){
            return new ErrorResponseDto(HttpStatus.BAD_REQUEST,ex.getMessage(), LocalDateTime.now());
        }

        @ExceptionHandler(ResourceNotFoundException.class)
        @ResponseStatus(HttpStatus.BAD_REQUEST)
        public ErrorResponseDto handleException(ResourceNotFoundException ex){
            return new ErrorResponseDto(HttpStatus.BAD_REQUEST, ex.getMessage(), LocalDateTime.now());
        }

        @ExceptionHandler(Exception.class)
        @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
        public ErrorResponseDto handleException(Exception ex){
            return new ErrorResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), LocalDateTime.now());
        }
    }
}
