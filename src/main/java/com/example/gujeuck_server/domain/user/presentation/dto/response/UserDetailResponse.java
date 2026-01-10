package com.example.gujeuck_server.domain.user.presentation.dto.response;

import com.example.gujeuck_server.domain.user.domain.enums.Gender;

public record UserDetailResponse(
        String name,
        String userId,
        String phone,
        Gender gender,
        String birthYMD,
        String residence
) {
}
