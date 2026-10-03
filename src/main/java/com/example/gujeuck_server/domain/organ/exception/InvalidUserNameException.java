package com.example.gujeuck_server.domain.organ.exception;

import com.example.gujeuck_server.global.error.exception.ErrorCode;
import com.example.gujeuck_server.global.error.exception.GujeukException;

public class InvalidUserNameException extends GujeukException {
    public static final GujeukException EXCEPTION = new InvalidUserNameException();

    private InvalidUserNameException() { super(ErrorCode.INVALID_USER_NAME); }
}