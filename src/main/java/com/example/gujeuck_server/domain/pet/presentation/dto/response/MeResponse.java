package com.example.gujeuck_server.domain.pet.presentation.dto.response;

import com.example.gujeuck_server.domain.pet.domain.PetUser;

public record MeResponse(
        UserSummary user,
        boolean hasPet
) {
    public static MeResponse of(PetUser petUser, boolean hasPet) {
        return new MeResponse(new UserSummary(String.valueOf(petUser.getId()), petUser.getName()), hasPet);
    }

    public record UserSummary(
            String userId,
            String name
    ) {
    }
}
