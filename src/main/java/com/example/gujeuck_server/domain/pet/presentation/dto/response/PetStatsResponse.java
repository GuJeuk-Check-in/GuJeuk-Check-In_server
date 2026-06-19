package com.example.gujeuck_server.domain.pet.presentation.dto.response;

public record PetStatsResponse(
        int hunger,
        int happy,
        int clean,
        int energy
) {
}
