package com.example.gujeuck_server.infrastructure.s3.exception;

import com.example.gujeuck_server.global.error.exception.ErrorCode;
import com.example.gujeuck_server.global.error.exception.GujeukException;

public class FailedDeleteException extends GujeukException {
    public static final GujeukException EXCEPTION = new FailedDeleteException();

    private FailedDeleteException() {
        super(ErrorCode.FAILED_DELETE);
    }
}
