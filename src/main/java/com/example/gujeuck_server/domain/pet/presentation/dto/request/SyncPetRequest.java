package com.example.gujeuck_server.domain.pet.presentation.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SyncPetRequest {
    @Valid
    @NotNull(message = "stats는 필수입니다.")
    private Stats stats;

    @Getter
    @NoArgsConstructor
    public static class Stats {
        @Min(value = 0, message = "hunger는 0 이상이어야 합니다.")
        @Max(value = 100, message = "hunger는 100 이하여야 합니다.")
        private int hunger;

        @Min(value = 0, message = "happy는 0 이상이어야 합니다.")
        @Max(value = 100, message = "happy는 100 이하여야 합니다.")
        private int happy;

        @Min(value = 0, message = "clean은 0 이상이어야 합니다.")
        @Max(value = 100, message = "clean은 100 이하여야 합니다.")
        private int clean;

        @Min(value = 0, message = "energy는 0 이상이어야 합니다.")
        @Max(value = 100, message = "energy는 100 이하여야 합니다.")
        private int energy;
    }
}
