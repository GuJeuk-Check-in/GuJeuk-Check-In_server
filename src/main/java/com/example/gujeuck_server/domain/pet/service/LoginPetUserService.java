package com.example.gujeuck_server.domain.pet.service;

import com.example.gujeuck_server.domain.pet.domain.PetUser;
import com.example.gujeuck_server.domain.pet.domain.repository.PetRepository;
import com.example.gujeuck_server.domain.pet.domain.repository.PetUserRepository;
import com.example.gujeuck_server.domain.pet.exception.InvalidPetGameLoginInputException;
import com.example.gujeuck_server.domain.pet.presentation.dto.request.PetGameLoginRequest;
import com.example.gujeuck_server.domain.pet.presentation.dto.request.PetLoginRequest;
import com.example.gujeuck_server.domain.pet.presentation.dto.response.PetGameLoginResponse;
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
        User existingUser = findExistingUser(request.getName(), request.getPhone());
        PetUser petUser = findOrCreatePetUser(existingUser);

        boolean hasPet = petRepository.existsByPetUserId(petUser.getId());
        String accessToken = jwtTokenProvider.createPetUserAccessToken(petUser.getId());

        return PetLoginResponse.of(accessToken, petUser, hasPet);
    }

    @Transactional
    public PetGameLoginResponse executeForPetGame(PetGameLoginRequest request) {
        User existingUser = findExistingUser(request.getName(), request.getPhone());
        findOrCreatePetUser(existingUser);

        return PetGameLoginResponse.success(existingUser);
    }

    private User findExistingUser(String rawName, String rawPhone) {
        String normalizedName = normalizeName(rawName);
        String normalizedPhone = normalizePhone(rawPhone);

        validateInput(normalizedName, normalizedPhone);

        return userRepository.findAllByNormalizedPhone(normalizedPhone).stream()
                .filter(user -> normalizeName(user.getName()).equals(normalizedName))
                .findFirst()
                .orElseThrow(() -> UserNotFoundException.EXCEPTION);
    }

    private PetUser findOrCreatePetUser(User existingUser) {
        String normalizedPhone = normalizePhone(existingUser.getPhone());

        return petUserRepository.findByNormalizedPhone(normalizedPhone)
                .map(existing -> {
                    existing.syncProfile(existingUser.getName(), existingUser.getPhone());
                    return existing;
                })
                .orElseGet(() -> petUserRepository.save(PetUser.builder()
                        .name(existingUser.getName())
                        .phone(existingUser.getPhone())
                        .build()));
    }

    private void validateInput(String normalizedName, String normalizedPhone) {
        if (normalizedName.isBlank() || normalizedPhone.length() < 9) {
            throw new InvalidPetGameLoginInputException();
        }
    }

    private String normalizeName(String value) {
        return value == null ? "" : value.replaceAll("\\s+", "").trim();
    }

    private String normalizePhone(String value) {
        return value == null ? "" : value.replaceAll("\\D", "");
    }
}
