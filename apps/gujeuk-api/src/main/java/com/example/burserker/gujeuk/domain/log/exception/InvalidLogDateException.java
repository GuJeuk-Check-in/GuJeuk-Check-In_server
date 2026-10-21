package com.example.burserker.gujeuk.domain.log.exception;

import com.example.burserker.gujeuk.global.error.exception.ErrorCode;
import com.example.burserker.gujeuk.global.error.exception.GujeukException;

public class InvalidLogDateException extends GujeukException {
    public static final GujeukException EXCEPTION = new InvalidLogDateException();

    public InvalidLogDateException() {
        super(ErrorCode.INVALID_LOG_DATE);
    }
}
