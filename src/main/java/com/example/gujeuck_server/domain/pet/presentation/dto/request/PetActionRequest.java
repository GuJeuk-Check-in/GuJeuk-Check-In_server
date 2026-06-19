package com.example.gujeuck_server.domain.pet.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PetActionRequest {
    @NotBlank(message = "action을 입력해주세요.")
    private String action;
}
