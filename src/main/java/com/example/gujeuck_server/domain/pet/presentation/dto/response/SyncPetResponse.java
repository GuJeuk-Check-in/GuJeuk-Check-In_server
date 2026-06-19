package com.example.gujeuck_server.domain.pet.presentation.dto.response;

public record SyncPetResponse(
        boolean ok,
        String lastSeenAt
) {
}
