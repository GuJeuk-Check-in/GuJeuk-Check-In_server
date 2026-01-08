package com.example.gujeuck_server.domain.user.presentation.dto.response;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SignUpResponse {
    @NotBlank
    private String userId;
}
