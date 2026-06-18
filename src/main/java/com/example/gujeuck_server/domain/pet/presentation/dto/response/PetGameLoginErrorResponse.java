package com.example.gujeuck_server.domain.pet.presentation.dto.response;

public record PetGameLoginErrorResponse(
        boolean success,
        String error,
        String message
) {
    public static PetGameLoginErrorResponse of(String error, String message) {
        return new PetGameLoginErrorResponse(false, error, message);
    }
}
