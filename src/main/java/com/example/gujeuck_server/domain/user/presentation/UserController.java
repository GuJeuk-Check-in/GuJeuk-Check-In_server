package com.example.gujeuck_server.domain.user.presentation;

import com.example.gujeuck_server.domain.user.presentation.dto.request.SignupRequest;
import com.example.gujeuck_server.domain.user.presentation.dto.request.UserCheckInRequest;
import com.example.gujeuck_server.domain.user.presentation.dto.request.UserExistsRequest;
import com.example.gujeuck_server.domain.user.presentation.dto.response.UserExistsResponse;
import com.example.gujeuck_server.domain.user.service.SignupService;
import com.example.gujeuck_server.domain.user.service.UserCheckInService;
import com.example.gujeuck_server.domain.user.service.UserExistsService;
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
    private final UserExistsService userExistsService;
    private final UserCheckInService userCheckInService;

    @PostMapping()
    public UserExistsResponse existsUser(@RequestBody @Valid UserExistsRequest request) {
        return userExistsService.execute(request);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/sign-up")
    public void signup(@RequestBody @Valid SignupRequest request) {
        signupService.execute(request);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/check-in")
    public void checkIn(@RequestBody @Valid UserCheckInRequest request) {
        userCheckInService.execute(request);
    }
}
