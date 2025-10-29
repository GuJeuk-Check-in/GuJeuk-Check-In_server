package com.example.gujeuck_server.domain.log.exception;

import com.example.gujeuck_server.global.error.exception.ErrorCode;
import com.example.gujeuck_server.global.error.exception.GujeukException;

public class DuplicateLogException extends GujeukException {
    public static final GujeukException EXCEPTION = new DuplicateLogException();

    private DuplicateLogException() {
        super(ErrorCode.DUPLICATE_LOG);
    }
}
