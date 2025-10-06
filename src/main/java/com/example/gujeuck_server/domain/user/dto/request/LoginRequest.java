package com.example.gujeuck_server.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class LoginRequest {
    @NotBlank
    private String userId;
}
