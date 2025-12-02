package com.example.gujeuck_server.domain.admin.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@Builder
public class AccessTokenResponse {
    private String accessToken;
    private Date accessExpiredAt;
}
