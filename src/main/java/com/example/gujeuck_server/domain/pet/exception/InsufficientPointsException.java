package com.example.gujeuck_server.domain.pet.exception;

import com.example.gujeuck_server.global.error.exception.ErrorCode;
import com.example.gujeuck_server.global.error.exception.GujeukException;

public class InsufficientPointsException extends GujeukException {
    public static final GujeukException EXCEPTION = new InsufficientPointsException();

    private InsufficientPointsException() {
        super(ErrorCode.INSUFFICIENT_POINTS);
    }
}
