package com.example.gujeuck_server.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.util.List;

@Getter
public class LoginRequest {

    @NotBlank
    private String userId;

    @NotBlank
    private String purpose;

    private List<String> companionIds;
}
