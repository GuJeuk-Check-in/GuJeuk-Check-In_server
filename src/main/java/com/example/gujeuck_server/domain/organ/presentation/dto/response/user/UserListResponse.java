package com.example.gujeuck_server.domain.organ.presentation.dto.response.user;

import com.example.gujeuck_server.domain.user.domain.User;

import com.example.gujeuck_server.domain.user.domain.enums.Gender;
import lombok.Builder;

@Builder
public record UserListResponse(
        String name,
        Gender gender,
        String phone,
        String birthYMD,
        String residence,
        int count
) {
    public static UserListResponse from(User user) {
        return UserListResponse.builder()
                .name(user.getName())
                .gender(user.getGender())
                .phone(user.getPhone())
                .birthYMD(user.getBirthYMD())
                .residence(user.getResidence())
                .count(user.getCount())
                .build();
    }
}
