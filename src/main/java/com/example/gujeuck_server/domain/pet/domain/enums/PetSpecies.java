package com.example.gujeuck_server.domain.pet.domain.enums;

import com.example.gujeuck_server.domain.pet.exception.InvalidPetSpeciesException;

public enum PetSpecies {
    MONG("mong"),
    POSIL("posil"),
    HOYA("hoya");

    private final String id;

    PetSpecies(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public static PetSpecies from(String value) {
        for (PetSpecies species : values()) {
            if (species.id.equalsIgnoreCase(value)) {
                return species;
            }
        }

        throw InvalidPetSpeciesException.EXCEPTION;
    }
}
