package com.example.gujeuck_server.domain.user.service;

import com.example.gujeuck_server.domain.user.presentation.dto.request.HaDataLogRequest;
import com.example.gujeuck_server.domain.user.presentation.dto.request.UserCheckInRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserLogHaService {
    private final UserCheckInService userCheckInService;

    @Transactional
    public void execute(HaDataLogRequest request) {
        UserCheckInRequest userCheckInRequest = UserCheckInRequest.create(
            request.id(),
            request.maleCount(),
            request.femaleCount(),
            request.purpose(),
            request.visitTime()
        );

        userCheckInService.execute(userCheckInRequest);
    }
}
