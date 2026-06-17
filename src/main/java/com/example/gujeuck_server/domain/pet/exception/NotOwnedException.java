package com.example.gujeuck_server.domain.pet.exception;

import com.example.gujeuck_server.global.error.exception.ErrorCode;
import com.example.gujeuck_server.global.error.exception.GujeukException;

public class NotOwnedException extends GujeukException {
    public static final GujeukException EXCEPTION = new NotOwnedException();

    private NotOwnedException() {
        super(ErrorCode.NOT_OWNED);
    }
}
