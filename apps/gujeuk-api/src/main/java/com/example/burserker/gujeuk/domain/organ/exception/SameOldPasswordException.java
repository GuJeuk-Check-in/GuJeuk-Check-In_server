package com.example.burserker.gujeuk.domain.organ.exception;

import com.example.burserker.gujeuk.global.error.exception.ErrorCode;
import com.example.burserker.gujeuk.global.error.exception.GujeukException;

public class SameOldPasswordException extends GujeukException {
    public static final GujeukException EXCEPTION = new SameOldPasswordException();

    private SameOldPasswordException() {
        super(ErrorCode.SAME_OLD_PASSWORD);
    }
}
