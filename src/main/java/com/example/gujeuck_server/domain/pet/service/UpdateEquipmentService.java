package com.example.gujeuck_server.domain.pet.service;

import com.example.gujeuck_server.domain.pet.domain.Pet;
import com.example.gujeuck_server.domain.pet.exception.NotOwnedException;
import com.example.gujeuck_server.domain.pet.facade.PetFacade;
import com.example.gujeuck_server.domain.pet.facade.PetUserFacade;
import com.example.gujeuck_server.domain.pet.presentation.dto.request.UpdateEquipmentRequest;
import com.example.gujeuck_server.domain.pet.presentation.dto.response.PetEquippedResponse;
import com.example.gujeuck_server.domain.pet.presentation.dto.response.UpdateEquipmentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateEquipmentService {
    private final PetFacade petFacade;
    private final PetUserFacade petUserFacade;

    @Transactional
    public UpdateEquipmentResponse execute(UpdateEquipmentRequest request) {
        Pet pet = petFacade.getWithLockByPetUserId(petUserFacade.currentUser().getId());

        validateOwnedHat(pet, request.getHat());
        validateOwnedShirt(pet, request.getShirt());

        pet.equip(normalize(request.getHat()), normalize(request.getShirt()));

        return new UpdateEquipmentResponse(PetEquippedResponse.from(pet));
    }

    private void validateOwnedHat(Pet pet, String hat) {
        String value = normalize(hat);
        if (value != null && !pet.getOwnedHats().contains(value)) {
            throw NotOwnedException.EXCEPTION;
        }
    }

    private void validateOwnedShirt(Pet pet, String shirt) {
        String value = normalize(shirt);
        if (value != null && !pet.getOwnedShirts().contains(value)) {
            throw NotOwnedException.EXCEPTION;
        }
    }

    private String normalize(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}
