package com.example.burserker.gujeuk.domain.user.presentation.dto.response;

import lombok.Builder;

@Builder
public record UserExistsResponse(
        boolean userExists,
        Long userId
) {
    public static UserExistsResponse of(boolean userExists, Long userId) {
        return UserExistsResponse.builder()
                .userExists(userExists)
                .userId(userId)
                .build();
    }
}
