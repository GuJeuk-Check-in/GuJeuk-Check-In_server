package com.example.gujeuck_server.domain.pet.facade;

import com.example.gujeuck_server.domain.pet.domain.Pet;
import com.example.gujeuck_server.domain.pet.domain.repository.PetRepository;
import com.example.gujeuck_server.domain.pet.exception.PetNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PetFacade {
    private final PetRepository petRepository;

    public Pet getByPetUserId(Long petUserId) {
        return petRepository.findByPetUserId(petUserId)
                .orElseThrow(() -> PetNotFoundException.EXCEPTION);
    }

    public Pet getWithLockByPetUserId(Long petUserId) {
        return petRepository.findWithLockByPetUserId(petUserId)
                .orElseThrow(() -> PetNotFoundException.EXCEPTION);
    }
}
