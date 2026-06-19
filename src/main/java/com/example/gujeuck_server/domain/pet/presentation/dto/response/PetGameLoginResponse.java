package com.example.gujeuck_server.domain.pet.presentation.dto.response;

import com.example.gujeuck_server.domain.user.domain.User;

public record PetGameLoginResponse(
        boolean success,
        String userId,
        String name,
        String phone
) {
    public static PetGameLoginResponse success(User user) {
        return new PetGameLoginResponse(
                true,
                user.getUserId(),
                user.getName(),
                user.getPhone()
        );
    }
}
