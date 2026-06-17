package com.example.gujeuck_server.domain.pet.service;

import com.example.gujeuck_server.domain.pet.domain.PetUser;
import com.example.gujeuck_server.domain.pet.domain.repository.PetRepository;
import com.example.gujeuck_server.domain.pet.domain.repository.PetUserRepository;
import com.example.gujeuck_server.domain.pet.presentation.dto.request.PetLoginRequest;
import com.example.gujeuck_server.domain.pet.presentation.dto.response.PetLoginResponse;
import com.example.gujeuck_server.domain.user.domain.User;
import com.example.gujeuck_server.domain.user.domain.repository.UserRepository;
import com.example.gujeuck_server.domain.user.exception.UserNotFoundException;
import com.example.gujeuck_server.global.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LoginPetUserService {
    private final PetUserRepository petUserRepository;
    private final PetRepository petRepository;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public PetLoginResponse execute(PetLoginRequest request) {
        String name = request.getName().trim();
        String phone = request.getPhone().trim();

        User existingUser = userRepository.findByNameAndPhone(name, phone)
                .orElseThrow(() -> UserNotFoundException.EXCEPTION);

        PetUser petUser = petUserRepository.findByPhone(phone)
                .map(existing -> {
                    existing.syncProfile(existingUser.getName(), existingUser.getPhone());
                    return existing;
                })
                .orElseGet(() -> petUserRepository.save(PetUser.builder()
                        .name(existingUser.getName())
                        .phone(existingUser.getPhone())
                        .build()));

        boolean hasPet = petRepository.existsByPetUserId(petUser.getId());
        String accessToken = jwtTokenProvider.createPetUserAccessToken(petUser.getId());

        return PetLoginResponse.of(accessToken, petUser, hasPet);
    }
}
