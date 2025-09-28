package com.example.gujeuck_server.infrastructure.s3.exception;

import com.example.gujeuck_server.global.error.exception.ErrorCode;
import com.example.gujeuck_server.global.error.exception.GujeukException;

public class FailedUploadException extends GujeukException {
    public static final GujeukException EXCEPTION = new FailedUploadException();

    private FailedUploadException() {
        super(ErrorCode.FAILED_UPLOAD);
    }
}
