package com.example.gujeuck_server.domain.user.presentation.dto.response;

import com.example.gujeuck_server.domain.user.domain.User;
import com.example.gujeuck_server.domain.user.domain.enums.Gender;

public record UserDetailResponse(
        Long id,
        String name,
        String userId,
        Gender gender,
        String phone,
        String birthYMD,
        String residence
) {
    public static UserDetailResponse from(User user) {
        return new UserDetailResponse(
                user.getId(),
                user.getName(),
                user.getUserId(),
                user.getGender(),
                user.getPhone(),
                user.getBirthYMD(),
                user.getResidence()
        );
    }
}
