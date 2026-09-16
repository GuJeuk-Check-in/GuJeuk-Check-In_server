package com.example.gujeuck_server.domain.residence.presentation.dto.request;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record ResidenceMoveRequest(
    @NotEmpty(message = "거주지 순서가 유효하지 않습니다.")
    List<Long> residenceId
) {
}
