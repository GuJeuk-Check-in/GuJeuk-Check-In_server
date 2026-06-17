package com.example.gujeuck_server.domain.pet.presentation.dto.response;

public record ShopItemResponse(
        String id,
        String name,
        int cost,
        String effect
) {
}
