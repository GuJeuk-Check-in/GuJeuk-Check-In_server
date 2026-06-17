package com.example.gujeuck_server.domain.pet.exception;

import com.example.gujeuck_server.global.error.exception.ErrorCode;
import com.example.gujeuck_server.global.error.exception.GujeukException;

public class InvalidPetItemException extends GujeukException {
    public static final GujeukException EXCEPTION = new InvalidPetItemException();

    private InvalidPetItemException() {
        super(ErrorCode.INVALID_PET_ITEM);
    }
}
