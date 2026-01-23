package com.example.gujeuck_server.domain.log.exception;

import com.example.gujeuck_server.global.error.exception.ErrorCode;
import com.example.gujeuck_server.global.error.exception.GujeukException;

public class InvalidLogDateException extends GujeukException {
    public static final GujeukException EXCEPTION = new InvalidLogDateException();

    public InvalidLogDateException() {
        super(ErrorCode.INVALID_LOG_DATE);
    }
}
