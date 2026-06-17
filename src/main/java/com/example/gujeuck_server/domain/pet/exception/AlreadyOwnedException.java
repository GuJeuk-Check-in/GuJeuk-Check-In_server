package com.example.gujeuck_server.domain.pet.exception;

import com.example.gujeuck_server.global.error.exception.ErrorCode;
import com.example.gujeuck_server.global.error.exception.GujeukException;

public class AlreadyOwnedException extends GujeukException {
    public static final GujeukException EXCEPTION = new AlreadyOwnedException();

    private AlreadyOwnedException() {
        super(ErrorCode.ALREADY_OWNED);
    }
}
