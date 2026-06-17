package com.example.gujeuck_server.domain.pet.presentation.dto.response;

import java.util.List;

public record InventoryResponse(
        PetInventoryResponse inventory,
        PetEquippedResponse equipped,
        List<String> placedProps
) {
}
