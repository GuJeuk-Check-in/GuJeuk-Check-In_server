package com.example.gujeuck_server.domain.pet.service;

import com.example.gujeuck_server.domain.pet.domain.Pet;
import com.example.gujeuck_server.domain.pet.domain.PetUser;
import com.example.gujeuck_server.domain.pet.domain.enums.PetSpecies;
import com.example.gujeuck_server.domain.pet.domain.repository.PetRepository;
import com.example.gujeuck_server.domain.pet.exception.PetAlreadyExistsException;
import com.example.gujeuck_server.domain.pet.facade.PetUserFacade;
import com.example.gujeuck_server.domain.pet.presentation.dto.request.CreatePetRequest;
import com.example.gujeuck_server.domain.pet.presentation.dto.response.PetResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashSet;

@Service
@RequiredArgsConstructor
public class CreatePetService {
    private final PetUserFacade petUserFacade;
    private final PetRepository petRepository;
    private final PetStateService petStateService;

    @Transactional
    public PetResponse execute(CreatePetRequest request) {
        PetUser petUser = petUserFacade.currentUser();
        if (petRepository.existsByPetUserId(petUser.getId())) {
            throw PetAlreadyExistsException.EXCEPTION;
        }

        Pet pet = Pet.builder()
                .petUser(petUser)
                .species(PetSpecies.from(request.getSpecies().trim()))
                .name(request.getName().trim())
                .xp(0)
                .points(5)
                .streak(0)
                .maxStreak(0)
                .visits(0)
                .lastSeenAt(petStateService.nowInstant())
                .hunger(72)
                .happy(78)
                .clean(82)
                .energy(72)
                .ownedHats(new LinkedHashSet<>())
                .ownedShirts(new LinkedHashSet<>())
                .ownedProps(new LinkedHashSet<>())
                .ownedBuffs(new LinkedHashSet<>())
                .placedProps(new ArrayList<>())
                .build();

        return PetResponse.from(petRepository.save(pet));
    }
}
