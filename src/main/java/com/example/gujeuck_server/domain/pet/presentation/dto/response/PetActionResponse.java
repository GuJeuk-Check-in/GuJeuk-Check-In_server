package com.example.gujeuck_server.domain.pet.presentation.dto.response;

public record PetActionResponse(
        int points,
        PetStatsResponse stats,
        int xp,
        int stage,
        boolean leveledUp
) {
}
