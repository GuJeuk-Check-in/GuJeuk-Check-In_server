package com.example.burserker.gujeuk.domain.organ.exception;

import com.example.burserker.gujeuk.global.error.exception.ErrorCode;
import com.example.burserker.gujeuk.global.error.exception.GujeukException;

public class InvalidPasswordConfirmException extends GujeukException {
    public static final GujeukException EXCEPTION = new InvalidPasswordConfirmException();

    private InvalidPasswordConfirmException() {
        super(ErrorCode.INVALID_PASSWORD_CONFIRM);
    }
}
