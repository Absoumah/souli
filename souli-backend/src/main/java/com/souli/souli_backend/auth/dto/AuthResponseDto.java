package com.souli.souli_backend.auth.dto;

public class AuthResponseDto {

    private String accessToken;
    private String tokenType;
    private long expiresIn;

    public AuthResponseDto(String accessToken, String tokenType, long expiresIn) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.expiresIn = expiresIn;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public long getExpiresIn() {
        return expiresIn;
    }
}
