package com.example.gujeuck_server.domain.residence.exception;

import com.example.gujeuck_server.global.error.exception.ErrorCode;
import com.example.gujeuck_server.global.error.exception.GujeukException;

public class ResidenceAlreadyException extends GujeukException {
    public static final GujeukException EXCEPTION = new ResidenceAlreadyException();

    private ResidenceAlreadyException() {
        super(ErrorCode.RESIDENCE_ALREADY_EXIST);
    }
}
