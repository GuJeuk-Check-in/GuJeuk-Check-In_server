package com.example.gujeuck_server.domain.pet.service;

import com.example.gujeuck_server.domain.pet.domain.PetUser;
import com.example.gujeuck_server.domain.pet.domain.repository.PetRepository;
import com.example.gujeuck_server.domain.pet.domain.repository.PetUserRepository;
import com.example.gujeuck_server.domain.pet.presentation.dto.request.PetLoginRequest;
import com.example.gujeuck_server.domain.pet.presentation.dto.response.PetLoginResponse;
import com.example.gujeuck_server.global.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LoginPetUserService {
    private final PetUserRepository petUserRepository;
    private final PetRepository petRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public PetLoginResponse execute(PetLoginRequest request) {
        PetUser petUser = petUserRepository.findByPhone(request.getPhone().trim())
                .map(existing -> existing)
                .orElseGet(() -> petUserRepository.save(PetUser.builder()
                        .name(request.getName().trim())
                        .phone(request.getPhone().trim())
                        .build()));

        boolean hasPet = petRepository.existsByPetUserId(petUser.getId());
        String accessToken = jwtTokenProvider.createPetUserAccessToken(petUser.getId());

        return PetLoginResponse.of(accessToken, petUser, hasPet);
    }
}
