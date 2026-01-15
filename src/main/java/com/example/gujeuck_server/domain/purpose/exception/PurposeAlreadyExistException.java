package com.example.gujeuck_server.domain.purpose.exception;

import com.example.gujeuck_server.global.error.exception.ErrorCode;
import com.example.gujeuck_server.global.error.exception.GujeukException;

public class PurposeAlreadyExistException extends GujeukException {

    public static final GujeukException EXCEPTION = new PurposeAlreadyExistException();

    private PurposeAlreadyExistException() {
        super(ErrorCode.PURPOSE_ALREADY_EXIST);
    }
}
