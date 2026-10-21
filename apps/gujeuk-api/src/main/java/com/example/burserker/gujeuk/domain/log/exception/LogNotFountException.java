package com.example.burserker.gujeuk.domain.log.exception;

import com.example.burserker.gujeuk.global.error.exception.ErrorCode;
import com.example.burserker.gujeuk.global.error.exception.GujeukException;

public class LogNotFountException extends GujeukException {
    public static final GujeukException EXCEPTION = new LogNotFountException();

    public LogNotFountException() {
        super(ErrorCode.LOG_NOT_FOUND);
    }
}
