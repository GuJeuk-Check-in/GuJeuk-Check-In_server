package com.example.gujeuck_server.domain.pet.presentation.dto.response;

import com.example.gujeuck_server.domain.pet.domain.Pet;

import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;

public record PetResponse(
        String petId,
        String species,
        String name,
        int stage,
        int xp,
        PetStatsResponse stats,
        int points,
        int streak,
        int maxStreak,
        int visits,
        String lastCheckinDate,
        String lastSeenAt,
        PetInventoryResponse inventory,
        PetEquippedResponse equipped,
        List<String> placedProps
) {
    public static PetResponse from(Pet pet) {
        return new PetResponse(
                String.valueOf(pet.getId()),
                pet.getSpecies().getId(),
                pet.getName(),
                pet.stage(),
                pet.getXp(),
                new PetStatsResponse(pet.getHunger(), pet.getHappy(), pet.getClean(), pet.getEnergy()),
                pet.getPoints(),
                pet.getStreak(),
                pet.getMaxStreak(),
                pet.getVisits(),
                pet.getLastCheckinDate() == null ? null : pet.getLastCheckinDate().toString(),
                DateTimeFormatter.ISO_INSTANT.format(pet.getLastSeenAt().atOffset(ZoneOffset.UTC)),
                PetInventoryResponse.from(pet),
                PetEquippedResponse.from(pet),
                List.copyOf(pet.getPlacedProps())
        );
    }
}
