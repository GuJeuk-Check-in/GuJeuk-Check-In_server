package com.example.burserker.gujeuk.domain.purpose.exception;

import com.example.burserker.gujeuk.global.error.exception.ErrorCode;
import com.example.burserker.gujeuk.global.error.exception.GujeukException;

public class PurposeAlreadyExistException extends GujeukException {
    public static final GujeukException EXCEPTION = new PurposeAlreadyExistException();

    private PurposeAlreadyExistException() {
        super(ErrorCode.PURPOSE_ALREADY_EXIST);
    }
}
