package com.example.gujeuck_server.domain.purpose.exception;

import com.example.gujeuck_server.global.error.exception.ErrorCode;
import com.example.gujeuck_server.global.error.exception.GujeukException;

public class PurposeNotFoundException extends GujeukException {
    public static final GujeukException EXCEPTION = new PurposeNotFoundException();

    public PurposeNotFoundException() {
        super(ErrorCode.PURPOSE_NOT_FOUND);
    };
}
