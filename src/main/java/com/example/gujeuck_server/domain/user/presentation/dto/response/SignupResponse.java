package com.example.gujeuck_server.domain.user.presentation.dto.response;

import lombok.Builder;

@Builder
public record SignupResponse(

        String userId
) {
}
