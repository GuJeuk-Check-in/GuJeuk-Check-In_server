package com.example.gujeuck_server.domain.residence.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Setter;

@Setter
@Builder
public class ResidenceRequest {
    @Size(min = 1, max = 30)
    @NotBlank(message = "빈칸으로 남겨둘 순 없습니다.")
    private String residenceName;
}
