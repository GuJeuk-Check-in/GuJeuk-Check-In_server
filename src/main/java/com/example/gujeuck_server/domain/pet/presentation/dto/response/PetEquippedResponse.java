package com.example.gujeuck_server.domain.pet.presentation.dto.response;

import com.example.gujeuck_server.domain.pet.domain.Pet;

public record PetEquippedResponse(
        String hat,
        String shirt
) {
    public static PetEquippedResponse from(Pet pet) {
        return new PetEquippedResponse(pet.getEquippedHat(), pet.getEquippedShirt());
    }
}
