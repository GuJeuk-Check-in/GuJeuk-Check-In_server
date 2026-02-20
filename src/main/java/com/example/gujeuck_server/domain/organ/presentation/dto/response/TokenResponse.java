package com.example.gujeuck_server.domain.organ.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TokenResponse {
    private String accessToken;
    private String refreshToken;
    private String organName;

    public TokenResponse(String accessToken, String refreshToken, String organName) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.organName = organName;
    }
}
