package com.example.gujeuck_server.domain.pet.exception;

import com.example.gujeuck_server.global.error.exception.ErrorCode;
import com.example.gujeuck_server.global.error.exception.GujeukException;

public class PetUserNotFoundException extends GujeukException {
    public static final GujeukException EXCEPTION = new PetUserNotFoundException();

    private PetUserNotFoundException() {
        super(ErrorCode.PET_USER_NOT_FOUND);
    }
}
