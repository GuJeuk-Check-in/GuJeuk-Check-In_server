package com.example.gujeuck_server.domain.log.exception;

import com.example.gujeuck_server.global.error.exception.ErrorCode;
import com.example.gujeuck_server.global.error.exception.GujeukException;

public class LogNotFountException extends GujeukException {
    public static final GujeukException EXCEPTION = new LogNotFountException();

    public LogNotFountException() {
        super(ErrorCode.LOG_NOT_FOUND);
    }
}
