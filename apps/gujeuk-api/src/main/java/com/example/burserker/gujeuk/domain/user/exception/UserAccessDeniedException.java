package com.example.burserker.gujeuk.domain.user.exception;

import com.example.burserker.gujeuk.global.error.exception.ErrorCode;
import com.example.burserker.gujeuk.global.error.exception.GujeukException;

public class UserAccessDeniedException extends GujeukException {
    public static final GujeukException EXCEPTION = new UserAccessDeniedException();

    private UserAccessDeniedException() {
        super(ErrorCode.USER_ACCESS_DENIED);
    }
}
