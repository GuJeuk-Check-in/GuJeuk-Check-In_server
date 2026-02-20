package com.example.gujeuck_server.domain.organ.exception;

import com.example.gujeuck_server.global.error.exception.ErrorCode;
import com.example.gujeuck_server.global.error.exception.GujeukException;

public class InvalidPasswordConfirmException extends GujeukException {
    public static final GujeukException EXCEPTION = new InvalidPasswordConfirmException();

    private InvalidPasswordConfirmException() {
        super(ErrorCode.INVALID_PASSWORD_CONFIRM);
    }
}
