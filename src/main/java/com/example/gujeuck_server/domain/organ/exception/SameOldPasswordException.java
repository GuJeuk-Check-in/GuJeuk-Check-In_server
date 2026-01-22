package com.example.gujeuck_server.domain.organ.exception;

import com.example.gujeuck_server.global.error.exception.ErrorCode;
import com.example.gujeuck_server.global.error.exception.GujeukException;

public class SameOldPasswordException extends GujeukException {
    public static final GujeukException EXCEPTION = new SameOldPasswordException();

    private SameOldPasswordException() {
        super(ErrorCode.SAME_OLD_PASSWORD);
    }
}
