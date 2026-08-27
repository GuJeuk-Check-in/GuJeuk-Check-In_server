package com.example.gujeuck_server.domain.user.presentation.dto.response;

import com.example.gujeuck_server.domain.user.domain.User;

public record UserInfoResponse(
        Long id,
        String name,
        String age,
        String gender,
        String phone,
        String birthYMD,
        String residence,
        boolean privacyAgreed,
        int count
) {
    public static UserInfoResponse from(User user) {
        return new UserInfoResponse(
                user.getId(),
                user.getName(),
                user.getAge().getLabel(),
                user.getGender().name(),
                user.getPhone(),
                user.getBirthYMD(),
                user.getResidence(),
                user.isPrivacyAgreed(),
                user.getCount()
        );
    }
}
