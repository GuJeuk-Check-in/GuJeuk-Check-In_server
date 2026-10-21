package com.example.burserker.gujeuk.domain.purpose.exception;

import com.example.burserker.gujeuk.global.error.exception.ErrorCode;
import com.example.burserker.gujeuk.global.error.exception.GujeukException;

public class PurposeNotFoundException extends GujeukException {
    public static final GujeukException EXCEPTION = new PurposeNotFoundException();

    private PurposeNotFoundException() {
        super(ErrorCode.PURPOSE_NOT_FOUND);
    };
}
