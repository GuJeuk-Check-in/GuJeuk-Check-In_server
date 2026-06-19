package com.example.gujeuck_server.domain.pet.presentation;

import com.example.gujeuck_server.domain.pet.presentation.dto.request.PetLoginRequest;
import com.example.gujeuck_server.domain.pet.presentation.dto.response.PetLoginResponse;
import com.example.gujeuck_server.domain.pet.service.LoginPetUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final LoginPetUserService loginPetUserService;

    @PostMapping("/login")
    public PetLoginResponse login(@RequestBody @Valid PetLoginRequest request) {
        return loginPetUserService.execute(request);
    }
}
