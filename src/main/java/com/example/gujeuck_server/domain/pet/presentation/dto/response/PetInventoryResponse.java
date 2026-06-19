package com.example.gujeuck_server.domain.pet.presentation.dto.response;

import com.example.gujeuck_server.domain.pet.domain.Pet;

import java.util.List;

public record PetInventoryResponse(
        List<String> hat,
        List<String> shirt,
        List<String> prop,
        List<String> buff
) {
    public static PetInventoryResponse from(Pet pet) {
        return new PetInventoryResponse(
                pet.getOwnedHats().stream().toList(),
                pet.getOwnedShirts().stream().toList(),
                pet.getOwnedProps().stream().toList(),
                pet.getOwnedBuffs().stream().toList()
        );
    }
}
