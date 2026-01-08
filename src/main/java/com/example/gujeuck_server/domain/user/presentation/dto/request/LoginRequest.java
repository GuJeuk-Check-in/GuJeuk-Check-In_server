package com.example.gujeuck_server.domain.user.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    @NotBlank
    private String userId;

    @NotBlank
    private String purpose;

    @NotNull
    private int maleCount;

    @NotNull
    private int femaleCount;

//    private List<String> companionIds;
}
