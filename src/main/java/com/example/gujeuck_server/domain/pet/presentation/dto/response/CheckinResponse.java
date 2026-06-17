package com.example.gujeuck_server.domain.pet.presentation.dto.response;

public record CheckinResponse(
        int awarded,
        boolean alreadyCheckedIn,
        int points,
        int streak,
        int maxStreak,
        String lastCheckinDate
) {
}
