package com.example.gujeuck_server.domain.user.presentation.dto.response;

import com.example.gujeuck_server.domain.user.domain.User;
import com.example.gujeuck_server.domain.user.domain.enums.Gender;

import lombok.Builder;

@Builder
public record UserDetailResponse(
        Long id,
        String name,
        Gender gender,
        String phone,
        String birthYMD,
        String residence
) {
    public static UserDetailResponse from(User user) {
        return UserDetailResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .gender(user.getGender())
                .phone(user.getPhone())
                .birthYMD(user.getBirthYMD())
                .residence(user.getResidence())
                .build();
    }
}
