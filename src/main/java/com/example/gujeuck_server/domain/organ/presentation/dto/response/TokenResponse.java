package com.example.gujeuck_server.domain.organ.presentation.dto.response;

public record TokenResponse(String accessToken, String refreshToken, String organName) {
    public static TokenResponse of(String accessToken, String refreshToken, String organName) {
        return new TokenResponse(accessToken, refreshToken, organName);
    }
}
