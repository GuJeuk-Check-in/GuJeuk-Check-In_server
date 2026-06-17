package com.example.gujeuck_server.domain.pet.service;

import com.example.gujeuck_server.domain.pet.domain.Pet;
import com.example.gujeuck_server.domain.pet.facade.PetFacade;
import com.example.gujeuck_server.domain.pet.facade.PetUserFacade;
import com.example.gujeuck_server.domain.pet.presentation.dto.response.CheckinResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class CheckinPetService {
    private final PetFacade petFacade;
    private final PetUserFacade petUserFacade;
    private final PetStateService petStateService;

    @Transactional
    public CheckinResponse execute() {
        Pet pet = petFacade.getWithLockByPetUserId(petUserFacade.currentUser().getId());
        LocalDate today = petStateService.today();

        if (today.equals(pet.getLastCheckinDate())) {
            return new CheckinResponse(
                    0,
                    true,
                    pet.getPoints(),
                    pet.getStreak(),
                    pet.getMaxStreak(),
                    pet.getLastCheckinDate() == null ? null : pet.getLastCheckinDate().toString()
            );
        }

        int streak = today.minusDays(1).equals(pet.getLastCheckinDate()) ? pet.getStreak() + 1 : 1;
        int maxStreak = Math.max(pet.getMaxStreak(), streak);
        int awarded = petStateService.calculateCheckinAward(pet);

        pet.awardCheckin(awarded, today, streak, maxStreak);

        return new CheckinResponse(awarded, false, pet.getPoints(), pet.getStreak(), pet.getMaxStreak(), today.toString());
    }
}
