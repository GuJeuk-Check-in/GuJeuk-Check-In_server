package com.example.gujeuck_server.domain.user.presentation.dto.request;

import com.example.gujeuck_server.domain.user.domain.enums.Gender;

public record UpdateUserRequest(
        String name,
        String userId,
        String phone,
        Gender gender,
        String birthYMD,
        String residence
) {
}
