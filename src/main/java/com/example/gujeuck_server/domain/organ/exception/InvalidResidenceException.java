package com.example.gujeuck_server.domain.organ.exception;

import com.example.gujeuck_server.global.error.exception.ErrorCode;
import com.example.gujeuck_server.global.error.exception.GujeukException;

public class InvalidResidenceException extends GujeukException {
    public static final GujeukException EXCEPTION = new InvalidResidenceException();

    private InvalidResidenceException() {
        super(ErrorCode.INVALID_RESIDENCE);
    }
}
