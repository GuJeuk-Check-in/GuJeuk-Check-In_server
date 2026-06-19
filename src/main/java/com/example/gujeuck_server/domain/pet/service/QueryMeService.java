package com.example.gujeuck_server.domain.pet.service;

import com.example.gujeuck_server.domain.pet.domain.PetUser;
import com.example.gujeuck_server.domain.pet.domain.repository.PetRepository;
import com.example.gujeuck_server.domain.pet.facade.PetUserFacade;
import com.example.gujeuck_server.domain.pet.presentation.dto.response.MeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QueryMeService {
    private final PetUserFacade petUserFacade;
    private final PetRepository petRepository;

    @Transactional(readOnly = true)
    public MeResponse execute() {
        PetUser petUser = petUserFacade.currentUser();
        return MeResponse.of(petUser, petRepository.existsByPetUserId(petUser.getId()));
    }
}
