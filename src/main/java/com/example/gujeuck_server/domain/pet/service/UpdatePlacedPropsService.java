package com.example.gujeuck_server.domain.pet.service;

import com.example.gujeuck_server.domain.pet.domain.Pet;
import com.example.gujeuck_server.domain.pet.exception.NotOwnedException;
import com.example.gujeuck_server.domain.pet.facade.PetFacade;
import com.example.gujeuck_server.domain.pet.facade.PetUserFacade;
import com.example.gujeuck_server.domain.pet.presentation.dto.request.UpdatePlacedPropsRequest;
import com.example.gujeuck_server.domain.pet.presentation.dto.response.UpdatePlacedPropsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UpdatePlacedPropsService {
    private final PetFacade petFacade;
    private final PetUserFacade petUserFacade;

    @Transactional
    public UpdatePlacedPropsResponse execute(UpdatePlacedPropsRequest request) {
        Pet pet = petFacade.getWithLockByPetUserId(petUserFacade.currentUser().getId());
        List<String> props = request.getPlacedProps().stream()
                .map(String::trim)
                .toList();

        if (!pet.getOwnedProps().containsAll(props)) {
            throw NotOwnedException.EXCEPTION;
        }

        pet.replacePlacedProps(props);

        return new UpdatePlacedPropsResponse(List.copyOf(pet.getPlacedProps()));
    }
}
