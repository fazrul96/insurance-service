package com.insurance.policy.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(WebException.class)
    public ResponseEntity<String> handleWebException(WebException ex) {
        return ResponseEntity
                .badRequest()
                .body(ex.getMessage());
    }
}