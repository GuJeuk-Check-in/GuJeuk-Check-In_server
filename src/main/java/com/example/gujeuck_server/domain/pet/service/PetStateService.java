package com.example.gujeuck_server.domain.pet.service;

import com.example.gujeuck_server.domain.pet.domain.Pet;
import com.example.gujeuck_server.global.utility.TimeProvider;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Component
public class PetStateService {
    private static final String SLOW_DECAY = "slowdecay";
    private static final String XP_BOOST = "xpboost";
    private static final String PT_BOOST = "ptboost";

    public void applyOfflineDecay(Pet pet) {
        Instant now = nowInstant();
        long elapsedMinutes = Duration.between(pet.getLastSeenAt(), now).toMinutes();

        if (elapsedMinutes <= 48L * 60L) {
            pet.markSeen(now);
            return;
        }

        double exceededHours = (elapsedMinutes - (48L * 60L)) / 60.0;
        double multiplier = pet.hasBuff(SLOW_DECAY) ? 0.5 : 1.0;

        int hungerDecrease = (int) Math.round(7 * exceededHours * multiplier);
        int happyDecrease = (int) Math.round(5 * exceededHours * multiplier);
        int cleanDecrease = (int) Math.round(4 * exceededHours * multiplier);
        int energyDecrease = (int) Math.round(4 * exceededHours * multiplier);

        pet.applyStats(
                pet.getHunger() - hungerDecrease,
                pet.getHappy() - happyDecrease,
                pet.getClean() - cleanDecrease,
                pet.getEnergy() - energyDecrease
        );
        pet.markSeen(now);
    }

    public int calculateActionXp(Pet pet, String targetStat, int baseXp) {
        int before = pet.readStat(targetStat);
        if (before >= 95) {
            return 0;
        }

        double room = (100 - before) / 100.0;
        double fullness = clamp(room * 1.3, 0.04, 1.0);

        int minStat = pet.minStat();
        double condition = minStat >= 50
                ? 1.0
                : 0.4 + 0.6 * (minStat / 50.0);

        double xpBoost = pet.hasBuff(XP_BOOST) ? 1.1 : 1.0;
        int calculated = (int) Math.round(baseXp * fullness * condition * xpBoost);

        return Math.max(calculated, 1);
    }

    public int calculateCheckinAward(Pet pet) {
        return pet.hasBuff(PT_BOOST) ? 4 : 3;
    }

    public LocalDate today() {
        return TimeProvider.nowDate();
    }

    public Instant nowInstant() {
        return TimeProvider.nowZoned().toInstant();
    }

    public String toIsoInstant(Instant instant) {
        return DateTimeFormatter.ISO_INSTANT.format(instant.atOffset(ZoneOffset.UTC));
    }

    private double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }
}
