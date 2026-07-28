package com.example.gujeuck_server.domain.user.presentation.dto.request;

import com.example.gujeuck_server.domain.user.domain.enums.Age;
import com.example.gujeuck_server.domain.user.domain.enums.Gender;

public record HaDataSignUpRequest(
        String name,
        String phone,
        Gender gender,
        String birthYMD,
        Age age,
        String residence,
        boolean privacyAgreed,
        int count,
        int maleCount,
        int femaleCount,
        String purpose
) {
}
