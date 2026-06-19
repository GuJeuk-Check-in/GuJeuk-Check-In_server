package com.example.gujeuck_server.domain.pet.service;

import com.example.gujeuck_server.domain.pet.domain.Pet;
import com.example.gujeuck_server.domain.pet.facade.PetFacade;
import com.example.gujeuck_server.domain.pet.facade.PetUserFacade;
import com.example.gujeuck_server.domain.pet.presentation.dto.response.InventoryResponse;
import com.example.gujeuck_server.domain.pet.presentation.dto.response.PetEquippedResponse;
import com.example.gujeuck_server.domain.pet.presentation.dto.response.PetInventoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryInventoryService {
    private final PetFacade petFacade;
    private final PetUserFacade petUserFacade;

    @Transactional(readOnly = true)
    public InventoryResponse execute() {
        Pet pet = petFacade.getByPetUserId(petUserFacade.currentUser().getId());
        return new InventoryResponse(PetInventoryResponse.from(pet), PetEquippedResponse.from(pet), List.copyOf(pet.getPlacedProps()));
    }
}
