package com.example.gujeuck_server.domain.user.presentation.dto.request;

import com.example.gujeuck_server.domain.user.domain.enums.Age;
import com.example.gujeuck_server.domain.user.domain.enums.Gender;

import java.time.LocalDateTime;

public record HaDataSignUpRequest(
        String clientRecordId,
        String name,
        String phone,
        Gender gender,
        String birthYMD,
        Age age,
        String residence,
        boolean privacyAgreed,
        int maleCount,
        int femaleCount,
        String purpose,
        LocalDateTime visitTime
) {
}
