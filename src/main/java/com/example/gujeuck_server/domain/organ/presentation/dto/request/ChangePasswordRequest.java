package com.example.gujeuck_server.domain.organ.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ChangePasswordRequest(
        @NotBlank
        String oldPassword,

        @NotBlank
        String newPassword,

        @NotBlank
        String confirmNewPassword
) {
}
