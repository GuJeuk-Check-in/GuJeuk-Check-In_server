package com.example.gujeuck_server.domain.organ.exception;

import com.example.gujeuck_server.global.error.exception.ErrorCode;
import com.example.gujeuck_server.global.error.exception.GujeukException;

public class UserNameNotFoundException extends GujeukException {
    public static final GujeukException EXCEPTION = new UserNameNotFoundException();

    public UserNameNotFoundException() { super(ErrorCode.USER_NAME_NOT_FOUND);}
}