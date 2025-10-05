package com.example.gujeuck_server.domain.user.controller;

import com.example.gujeuck_server.domain.user.dto.UserResponse;
import com.example.gujeuck_server.domain.user.dto.request.LoginRequest;
import com.example.gujeuck_server.domain.user.dto.request.RefreshTokenRequest;
import com.example.gujeuck_server.domain.user.dto.request.SignupRequest;
import com.example.gujeuck_server.domain.user.dto.response.TokenResponse;
import com.example.gujeuck_server.domain.user.service.LoginService;
import com.example.gujeuck_server.domain.user.service.ReadAllUserListService;
import com.example.gujeuck_server.domain.user.service.ReissueService;
import com.example.gujeuck_server.domain.user.service.SignupService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/user")
public class UserController {
    private final ReadAllUserListService readAllUserListService;
    private final SignupService signupService;
    private final LoginService loginService;
    private final ReissueService reissueService;

    @GetMapping("/all")
    public List<UserResponse> getAllUserList() {
        return readAllUserListService.readAllUserList();
    }

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
