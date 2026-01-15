package com.example.gujeuck_server.domain.log.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class QueryLogListByResidenceRequest {
    @NotBlank(message = "월을 선택해주세요")
    private int month;
}
