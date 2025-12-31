package com.example.gujeuck_server.domain.user.controller;

import com.example.gujeuck_server.domain.user.dto.request.LoginRequest;
import com.example.gujeuck_server.domain.user.dto.request.SignupRequest;
import com.example.gujeuck_server.domain.user.dto.response.SignUpResponse;
import com.example.gujeuck_server.domain.user.service.LoginService;
import com.example.gujeuck_server.domain.user.service.SignupService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final SignupService signupService;
    private final LoginService loginService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/sign-up")
    public SignUpResponse signup(@RequestBody @Valid SignupRequest request) {
        return signupService.signup(request);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/login")
    public void login(@RequestBody @Valid LoginRequest request) {
        loginService.login(request);
    }
}
