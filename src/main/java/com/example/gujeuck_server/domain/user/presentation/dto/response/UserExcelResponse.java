package com.example.gujeuck_server.domain.user.presentation.dto.response;

import com.example.gujeuck_server.domain.user.domain.User;
import com.example.gujeuck_server.domain.user.domain.enums.Age;
import com.example.gujeuck_server.domain.user.domain.enums.Gender;
import lombok.Builder;

@Builder
public record UserExcelResponse(
        String userId,
        String name,
        String phone,
        Gender gender,
        String birthYMD,
        Age age,
        String residence,
        boolean privacyAgreed,
        int count
) {
    // User 엔티티 -> UserExcelResponse 로 변환하는 정적 메서드
    public static UserExcelResponse from(User user) {
        return UserExcelResponse.builder()
                .userId(user.getUserId())
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
