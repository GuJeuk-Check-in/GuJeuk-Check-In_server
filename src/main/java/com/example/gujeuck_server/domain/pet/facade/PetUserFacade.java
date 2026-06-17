package com.example.gujeuck_server.domain.pet.facade;

import com.example.gujeuck_server.domain.pet.domain.PetUser;
import com.example.gujeuck_server.domain.pet.domain.repository.PetUserRepository;
import com.example.gujeuck_server.domain.pet.exception.PetUserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PetUserFacade {
    private final PetUserRepository petUserRepository;

    public PetUser currentUser() {
        String principal = SecurityContextHolder.getContext().getAuthentication().getName();

        try {
            Long petUserId = Long.parseLong(principal);
            return petUserRepository.findById(petUserId)
                    .orElseThrow(() -> PetUserNotFoundException.EXCEPTION);
        } catch (NumberFormatException exception) {
            throw PetUserNotFoundException.EXCEPTION;
        }
    }
}
