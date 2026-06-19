package com.example.gujeuck_server.domain.pet.exception;

import com.example.gujeuck_server.global.error.exception.ErrorCode;
import com.example.gujeuck_server.global.error.exception.GujeukException;

public class PetNotFoundException extends GujeukException {
    public static final GujeukException EXCEPTION = new PetNotFoundException();

    private PetNotFoundException() {
        super(ErrorCode.PET_NOT_FOUND);
    }
}
