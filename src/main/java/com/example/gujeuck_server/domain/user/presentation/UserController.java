package com.example.gujeuck_server.domain.user.presentation;

import com.example.gujeuck_server.domain.log.presentation.dto.request.LogRequest;
import com.example.gujeuck_server.domain.log.service.CreateLogService;
import com.example.gujeuck_server.domain.organ.facade.OrganFacade;
import com.example.gujeuck_server.domain.user.presentation.dto.request.LoginRequest;
import com.example.gujeuck_server.domain.user.presentation.dto.request.SignupRequest;
import com.example.gujeuck_server.domain.user.presentation.dto.response.SignUpResponse;
import com.example.gujeuck_server.domain.user.service.LoginUserService;
import com.example.gujeuck_server.domain.user.service.SignupService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private static final Long DEFAULT_CHECK_IN_ORGAN_ID = 1L;

    private final SignupService signupService;
    private final LoginUserService loginUserService;
    private final CreateLogService createLogService;
    private final OrganFacade organFacade;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/sign-up")
    public SignUpResponse signup(@RequestBody @Valid SignupRequest request) {
        return signupService.execute(request);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/login")
    public void login(@RequestBody @Valid LoginRequest request) {
        loginUserService.execute(request);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/check-in")
    public void checkIn(@RequestBody @Valid LogRequest request) {
        createLogService.execute(organFacade.getOrganById(DEFAULT_CHECK_IN_ORGAN_ID), request);
    }
}
