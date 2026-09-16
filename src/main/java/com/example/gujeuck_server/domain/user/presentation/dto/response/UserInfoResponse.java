package com.example.gujeuck_server.domain.user.presentation.dto.response;

import com.example.gujeuck_server.domain.user.domain.User;

import lombok.Builder;

@Builder
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
        return UserInfoResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .age(user.getAge().getLabel())
                .gender(user.getGender().name())
                .phone(user.getPhone())
                .birthYMD(user.getBirthYMD())
                .residence(user.getResidence())
                .privacyAgreed(user.isPrivacyAgreed())
                .count(user.getCount())
                .build();
    }
}
