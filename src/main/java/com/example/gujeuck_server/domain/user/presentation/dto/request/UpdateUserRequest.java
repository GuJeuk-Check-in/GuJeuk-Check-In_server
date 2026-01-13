package com.example.gujeuck_server.domain.user.presentation.dto.request;

import com.example.gujeuck_server.domain.user.domain.enums.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateUserRequest(
        @NotBlank
        String name,
        @NotBlank
        String userId,
        @NotBlank
        String phone,
        @NotNull
        Gender gender,
        @NotBlank
        String birthYMD,
        @NotBlank
        String residence
) {
}
