package com.example.gujeuck_server.domain.user.exception;

import com.example.gujeuck_server.global.error.exception.ErrorCode;
import com.example.gujeuck_server.global.error.exception.GujeukException;

public class UserAccessDeniedException extends GujeukException {
    public static final GujeukException EXCEPTION = new UserAccessDeniedException();

    private UserAccessDeniedException() {
        super(ErrorCode.USER_ACCESS_DENIED);
    }
}
