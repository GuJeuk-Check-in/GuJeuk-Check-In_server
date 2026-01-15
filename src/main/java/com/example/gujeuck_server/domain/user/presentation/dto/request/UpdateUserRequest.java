package com.example.gujeuck_server.domain.user.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UpdateUserRequest(
        @NotBlank
        String name,

        @NotBlank
        String userId,

        @NotBlank
        String phone,

        @NotBlank
        String birthYMD,

        @NotBlank
        String residence
) {
}
