package com.example.burserker.gujeuk.domain.organ.presentation.dto.response.auth;

import lombok.Builder;

@Builder
public record TokenResponse(
    String accessToken,
    String refreshToken,
    String organName
) {
    public static TokenResponse of(String accessToken, String refreshToken, String organName) {
        return TokenResponse.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .organName(organName)
            .build();
    }
}
