package com.souli.souli_backend.common.handler;

import com.souli.souli_backend.common.dto.ApiResponseDto;
import com.souli.souli_backend.common.exception.SouliException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SouliException.class)
    public ResponseEntity<ApiResponseDto<Void>> handleSouliException(
            SouliException exception
    ) {
        return buildResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    private ResponseEntity<ApiResponseDto<Void>> buildResponse(
            HttpStatus status,
            String message
    ) {
        ApiResponseDto<Void> body = new ApiResponseDto<>(
                status.value(),
                message,
                null
        );
        return ResponseEntity.status(status).body(body);
    }
}
