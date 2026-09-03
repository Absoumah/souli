package com.souli.souli_backend.auth.dto;

public record AuthResponseDto(
        String accessToken,
        String refreshToken,
        String tokenType,
        long expiresIn
) { }
