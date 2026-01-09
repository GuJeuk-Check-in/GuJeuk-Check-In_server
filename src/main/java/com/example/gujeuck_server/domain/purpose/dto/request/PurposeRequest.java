package com.example.gujeuck_server.domain.purpose.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class PurposeRequest {
    @NotBlank
    private Long id;

    @Size(min = 1, max = 30)
    @NotBlank
    private String purpose;
}
