package com.example.gujeuck_server.domain.user.presentation.dto.response;

import com.example.gujeuck_server.domain.user.domain.User;
import com.example.gujeuck_server.domain.user.domain.enums.Gender;

public record UserDetailResponse(
        String name,
        String userId,
        String phone,
        Gender gender,
        String birthYMD,
        String residence
) {
    public static UserDetailResponse from(User user) {
        return new UserDetailResponse(user.getName(), user.getUserId(), user.getPhone(), user.getGender(), user.getBirthYMD(), user.getResidence());
    }
}
