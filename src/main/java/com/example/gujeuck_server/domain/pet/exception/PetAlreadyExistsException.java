package com.example.gujeuck_server.domain.pet.exception;

import com.example.gujeuck_server.global.error.exception.ErrorCode;
import com.example.gujeuck_server.global.error.exception.GujeukException;

public class PetAlreadyExistsException extends GujeukException {
    public static final GujeukException EXCEPTION = new PetAlreadyExistsException();

    private PetAlreadyExistsException() {
        super(ErrorCode.PET_ALREADY_EXISTS);
    }
}
