package com.example.gujeuck_server.domain.pet.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PetGameLoginRequest {
    @NotBlank(message = "이름과 전화번호를 올바르게 입력해주세요.")
    @Size(max = 10, message = "이름과 전화번호를 올바르게 입력해주세요.")
    private String name;

    private String phone;
}
