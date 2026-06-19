package com.example.gujeuck_server.domain.pet.domain.enums;

public enum PetItemCategory {
    HAT("hat"),
    SHIRT("shirt"),
    PROP("prop"),
    BUFF("buff");

    private final String value;

    PetItemCategory(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
