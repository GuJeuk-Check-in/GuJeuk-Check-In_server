package com.example.burserker.gujeuk.domain.organ.exception;

import com.example.burserker.gujeuk.global.error.exception.ErrorCode;
import com.example.burserker.gujeuk.global.error.exception.GujeukException;

public class InvalidUserNameException extends GujeukException {
    public static final GujeukException EXCEPTION = new InvalidUserNameException();

    private InvalidUserNameException() { super(ErrorCode.INVALID_USER_NAME); }
}