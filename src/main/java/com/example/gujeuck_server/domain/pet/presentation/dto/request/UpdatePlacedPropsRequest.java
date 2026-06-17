package com.example.gujeuck_server.domain.pet.presentation.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class UpdatePlacedPropsRequest {
    @NotNull(message = "placedProps는 필수입니다.")
    private List<String> placedProps;
}
