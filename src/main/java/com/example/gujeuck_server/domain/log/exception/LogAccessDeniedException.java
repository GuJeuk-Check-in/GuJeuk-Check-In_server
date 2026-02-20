package com.example.gujeuck_server.domain.log.exception;

import com.example.gujeuck_server.global.error.exception.ErrorCode;
import com.example.gujeuck_server.global.error.exception.GujeukException;

public class LogAccessDeniedException extends GujeukException {
    public static final GujeukException EXCEPTION = new LogAccessDeniedException();

    private LogAccessDeniedException() {
        super(ErrorCode.LOG_ACCESS_DENIED);
    }
}