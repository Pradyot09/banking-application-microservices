package com.javaexpress.accounts.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
public class ErrorResponseDto {

    private HttpStatus httpStatus;
    private String errorMessage;
    private LocalDateTime localDateTime;
}
