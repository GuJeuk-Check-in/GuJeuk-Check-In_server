package com.example.gujeuck_server.domain.pet.domain.enums;

import com.example.gujeuck_server.domain.pet.exception.InvalidPetActionException;

public enum PetActionType {
    FEED("feed", "hunger", 1, 22, 5),
    PLAY("play", "happy", 2, 24, 10),
    CLEAN("clean", "clean", 2, 28, 6),
    SLEEP("sleep", "energy", 1, 30, 4);

    private final String value;
    private final String targetStat;
    private final int pointCost;
    private final int statIncrease;
    private final int baseXp;

    PetActionType(String value, String targetStat, int pointCost, int statIncrease, int baseXp) {
        this.value = value;
        this.targetStat = targetStat;
        this.pointCost = pointCost;
        this.statIncrease = statIncrease;
        this.baseXp = baseXp;
    }

    public String getValue() {
        return value;
    }

    public String getTargetStat() {
        return targetStat;
    }

    public int getPointCost() {
        return pointCost;
    }

    public int getStatIncrease() {
        return statIncrease;
    }

    public int getBaseXp() {
        return baseXp;
    }

    public static PetActionType from(String value) {
        for (PetActionType action : values()) {
            if (action.value.equalsIgnoreCase(value)) {
                return action;
            }
        }

        throw InvalidPetActionException.EXCEPTION;
    }
}
