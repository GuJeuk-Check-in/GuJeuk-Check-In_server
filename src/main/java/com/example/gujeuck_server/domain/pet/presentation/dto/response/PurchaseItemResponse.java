package com.example.gujeuck_server.domain.pet.presentation.dto.response;

public record PurchaseItemResponse(
        int points,
        PetInventoryResponse inventory,
        PetEquippedResponse equipped
) {
}
