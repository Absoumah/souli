package com.souli.souli_backend.common.dto;

public record ApiResponseDto<T>(
        int status,
        String message,
        T result
) { }
