package com.example.gujeuck_server.domain.pet.presentation;

import com.example.gujeuck_server.domain.pet.presentation.dto.request.PetGameLoginRequest;
import com.example.gujeuck_server.domain.pet.presentation.dto.response.PetGameLoginResponse;
import com.example.gujeuck_server.domain.pet.service.LoginPetUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pet-game")
@RequiredArgsConstructor
public class PetGameAuthController {
    private final LoginPetUserService loginPetUserService;

    @PostMapping("/login")
    public PetGameLoginResponse login(@RequestBody @Valid PetGameLoginRequest request) {
        return loginPetUserService.executeForPetGame(request);
    }
}
