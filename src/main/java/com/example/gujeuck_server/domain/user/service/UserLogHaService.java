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
    public void execute(List<HaDataLogRequest> request) {
        for(HaDataLogRequest haDataLogRequest : request) {
            UserCheckInRequest userCheckInRequest = UserCheckInRequest.create(
                haDataLogRequest.id(),
                haDataLogRequest.maleCount(),
                haDataLogRequest.femaleCount(),
                haDataLogRequest.purpose(),
                haDataLogRequest.visitTime()
            );

            userCheckInService.execute(userCheckInRequest);
        }
    }
}
