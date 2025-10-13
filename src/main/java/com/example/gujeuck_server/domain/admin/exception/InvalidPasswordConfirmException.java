package com.example.gujeuck_server.domain.admin.exception;

import com.example.gujeuck_server.global.error.exception.ErrorCode;
import com.example.gujeuck_server.global.error.exception.GujeukException;

public class InvalidPasswordConfirmException extends GujeukException {
    public static final InvalidPasswordConfirmException EXCEPTION = new InvalidPasswordConfirmException();

    private InvalidPasswordConfirmException() {
        super(ErrorCode.INVALID_PASSWORD_CONFIRM);
    }
}
