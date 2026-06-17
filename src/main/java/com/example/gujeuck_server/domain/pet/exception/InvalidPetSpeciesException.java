package com.example.gujeuck_server.domain.pet.exception;

import com.example.gujeuck_server.global.error.exception.ErrorCode;
import com.example.gujeuck_server.global.error.exception.GujeukException;

public class InvalidPetSpeciesException extends GujeukException {
    public static final GujeukException EXCEPTION = new InvalidPetSpeciesException();

    private InvalidPetSpeciesException() {
        super(ErrorCode.INVALID_PET_SPECIES);
    }
}
