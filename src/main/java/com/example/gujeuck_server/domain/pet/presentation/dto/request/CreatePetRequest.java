package com.example.gujeuck_server.domain.pet.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreatePetRequest {
    @NotBlank(message = "펫 종류를 입력해주세요.")
    private String species;

    @NotBlank(message = "펫 이름을 입력해주세요.")
    private String name;
}
