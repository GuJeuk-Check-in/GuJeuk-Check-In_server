package com.example.gujeuck_server.domain.purpose.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PurposeRequest(
        @Size(min = 1, max = 30)
        @NotBlank
        String purpose
) {
}
