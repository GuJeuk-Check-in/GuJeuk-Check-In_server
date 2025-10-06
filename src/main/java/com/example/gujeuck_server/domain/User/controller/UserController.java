package com.example.gujeuck_server.domain.User.controller;

import com.example.gujeuck_server.domain.User.dto.request.LoginRequest;
import com.example.gujeuck_server.domain.User.dto.request.RefreshTokenRequest;
import com.example.gujeuck_server.domain.User.dto.request.SignupRequest;
import com.example.gujeuck_server.domain.User.dto.response.TokenResponse;
import com.example.gujeuck_server.domain.User.service.LoginService;
import com.example.gujeuck_server.domain.User.service.ReissueService;
import com.example.gujeuck_server.domain.User.service.SignupService;
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
    private final ReissueService reissueService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/sign-up")
    public void signup(@RequestBody @Valid SignupRequest request) {
        signupService.signup(request);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/login")
    public TokenResponse login(@RequestBody @Valid LoginRequest request) {
        return loginService.login(request);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/re-issue")
    public TokenResponse reissue(@RequestBody @Valid RefreshTokenRequest request) {
        return reissueService.reissue(request);
    }
}
