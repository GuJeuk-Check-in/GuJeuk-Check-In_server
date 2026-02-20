package com.example.gujeuck_server.domain.purpose.exception;

import com.example.gujeuck_server.global.error.exception.ErrorCode;
import com.example.gujeuck_server.global.error.exception.GujeukException;

public class PurposeAccessDeniedException extends GujeukException {
    public static final GujeukException EXCEPTION = new PurposeAccessDeniedException();

    private PurposeAccessDeniedException() {
        super(ErrorCode.PURPOSE_ACCESS_DENIED);
    }
}
