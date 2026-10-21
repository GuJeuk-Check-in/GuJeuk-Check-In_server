package com.example.burserker.gujeuk.domain.log.exception;

import com.example.burserker.gujeuk.global.error.exception.ErrorCode;
import com.example.burserker.gujeuk.global.error.exception.GujeukException;

public class DuplicateLogException extends GujeukException {
    public static final GujeukException EXCEPTION = new DuplicateLogException();

    private DuplicateLogException() {
        super(ErrorCode.DUPLICATE_LOG);
    }
}
