package com.example.gujeuck_server.domain.user.presentation.dto.response;

import com.example.gujeuck_server.domain.user.domain.User;
import com.example.gujeuck_server.domain.user.domain.enums.Age;
import com.example.gujeuck_server.domain.user.domain.enums.Gender;
import lombok.Builder;

@Builder
public record UserExcelResponse(
        String name,
        String phone,
        Gender gender,
        String birthYMD,
        Age age,
        String residence,
        boolean privacyAgreed,
        int count
) {
    public static UserExcelResponse from(User user) {
        return UserExcelResponse.builder()
                .name(user.getName())
                .phone(user.getPhone())
                .gender(user.getGender())
                .birthYMD(user.getBirthYMD())
                .age(user.getAge())
                .residence(user.getResidence())
                .privacyAgreed(user.isPrivacyAgreed())
                .count(user.getCount())
                .build();
    }
}
