package com.example.gujeuck_server.domain.user.presentation;

import com.example.gujeuck_server.domain.user.presentation.dto.request.*;
import com.example.gujeuck_server.domain.user.presentation.dto.response.UserExistsResponse;
import com.example.gujeuck_server.domain.user.service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import software.amazon.awssdk.services.s3.endpoints.internal.Value;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private static final Long DEFAULT_CHECK_IN_ORGAN_ID = 1L;

    private final SignupService signupService;
    private final UserExistsService userExistsService;
    private final UserCheckInService userCheckInService;
    private final UserLogHaService userLogHaService;
    private final UserSignUpHaService userSignUpHaService;

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

    @PostMapping("/ha-log")
    public void haLog(@RequestBody @Valid List<HaDataLogRequest> requests) {
        userLogHaService.execute(requests);
    }

    @PostMapping("/ha-sign-up")
    public void haSingUp(@RequestBody @Valid List<HaDataSignUpRequest> requests) {
        userSignUpHaService.execute(requests);
    }
}
