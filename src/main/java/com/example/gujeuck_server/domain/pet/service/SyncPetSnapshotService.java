package com.example.gujeuck_server.domain.pet.service;

import com.example.gujeuck_server.domain.pet.domain.Pet;
import com.example.gujeuck_server.domain.pet.facade.PetFacade;
import com.example.gujeuck_server.domain.pet.facade.PetUserFacade;
import com.example.gujeuck_server.domain.pet.presentation.dto.request.SyncPetRequest;
import com.example.gujeuck_server.domain.pet.presentation.dto.response.SyncPetResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class SyncPetSnapshotService {
    private final PetFacade petFacade;
    private final PetUserFacade petUserFacade;
    private final PetStateService petStateService;

    @Transactional
    public SyncPetResponse execute(SyncPetRequest request) {
        Pet pet = petFacade.getWithLockByPetUserId(petUserFacade.currentUser().getId());
        pet.applyStats(
                request.getStats().getHunger(),
                request.getStats().getHappy(),
                request.getStats().getClean(),
                request.getStats().getEnergy()
        );
        Instant now = petStateService.nowInstant();
        pet.markSeen(now);

        return new SyncPetResponse(true, petStateService.toIsoInstant(now));
    }
}
