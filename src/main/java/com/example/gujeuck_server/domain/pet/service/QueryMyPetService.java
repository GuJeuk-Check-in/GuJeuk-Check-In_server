package com.example.gujeuck_server.domain.pet.service;

import com.example.gujeuck_server.domain.pet.domain.Pet;
import com.example.gujeuck_server.domain.pet.domain.repository.PetRepository;
import com.example.gujeuck_server.domain.pet.facade.PetUserFacade;
import com.example.gujeuck_server.domain.pet.presentation.dto.response.PetResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QueryMyPetService {
    private final PetUserFacade petUserFacade;
    private final PetRepository petRepository;
    private final PetStateService petStateService;

    @Transactional
    public PetResponse execute() {
        Pet pet = petRepository.findByPetUserId(petUserFacade.currentUser().getId())
                .orElseThrow(() -> com.example.gujeuck_server.domain.pet.exception.PetNotFoundException.EXCEPTION);
        petStateService.applyOfflineDecay(pet);

        return PetResponse.from(pet);
    }
}
