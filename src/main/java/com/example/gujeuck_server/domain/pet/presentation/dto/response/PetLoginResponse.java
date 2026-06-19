package com.example.gujeuck_server.domain.pet.presentation.dto.response;

import com.example.gujeuck_server.domain.pet.domain.PetUser;

public record PetLoginResponse(
        String accessToken,
        UserSummary user,
        boolean hasPet
) {
    public static PetLoginResponse of(String accessToken, PetUser petUser, boolean hasPet) {
        return new PetLoginResponse(
                accessToken,
                new UserSummary(String.valueOf(petUser.getId()), petUser.getName(), petUser.getPhone()),
                hasPet
        );
    }

    public record UserSummary(
            String userId,
            String name,
            String phone
    ) {
    }
}
