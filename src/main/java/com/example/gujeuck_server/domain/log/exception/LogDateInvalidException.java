package com.example.gujeuck_server.domain.log.exception;

import com.example.gujeuck_server.global.error.exception.ErrorCode;
import com.example.gujeuck_server.global.error.exception.GujeukException;

public class LogDateInvalidException extends GujeukException {
    public static final GujeukException EXCEPTION = new LogDateInvalidException();

    public LogDateInvalidException() {
        super(ErrorCode.LOG_NOT_FOUND);
    }
}
