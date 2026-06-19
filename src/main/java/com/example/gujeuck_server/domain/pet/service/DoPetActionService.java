package com.example.gujeuck_server.domain.pet.service;

import com.example.gujeuck_server.domain.pet.domain.Pet;
import com.example.gujeuck_server.domain.pet.domain.enums.PetActionType;
import com.example.gujeuck_server.domain.pet.exception.InsufficientPointsException;
import com.example.gujeuck_server.domain.pet.facade.PetFacade;
import com.example.gujeuck_server.domain.pet.facade.PetUserFacade;
import com.example.gujeuck_server.domain.pet.presentation.dto.request.PetActionRequest;
import com.example.gujeuck_server.domain.pet.presentation.dto.response.PetActionResponse;
import com.example.gujeuck_server.domain.pet.presentation.dto.response.PetStatsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DoPetActionService {
    private final PetFacade petFacade;
    private final PetUserFacade petUserFacade;
    private final PetStateService petStateService;

    @Transactional
    public PetActionResponse execute(PetActionRequest request) {
        Pet pet = petFacade.getWithLockByPetUserId(petUserFacade.currentUser().getId());
        petStateService.applyOfflineDecay(pet);

        PetActionType action = PetActionType.from(request.getAction().trim());
        if (pet.getPoints() < action.getPointCost()) {
            throw InsufficientPointsException.EXCEPTION;
        }

        int previousStage = pet.stage();
        int gainedXp = petStateService.calculateActionXp(pet, action.getTargetStat(), action.getBaseXp());

        pet.spendPoints(action.getPointCost());
        pet.increaseStat(action.getTargetStat(), action.getStatIncrease());
        pet.gainXp(gainedXp);
        pet.markSeen(petStateService.nowInstant());

        return new PetActionResponse(
                pet.getPoints(),
                new PetStatsResponse(pet.getHunger(), pet.getHappy(), pet.getClean(), pet.getEnergy()),
                pet.getXp(),
                pet.stage(),
                previousStage < pet.stage()
        );
    }
}
