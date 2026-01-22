package com.example.gujeuck_server.domain.organ.exception;

import com.example.gujeuck_server.global.error.exception.ErrorCode;
import com.example.gujeuck_server.global.error.exception.GujeukException;

public class OrganNotFoundException extends GujeukException {
    public static final GujeukException EXCEPTION = new OrganNotFoundException();

    private OrganNotFoundException() {
        super(ErrorCode.ORGAN_NOT_FOUND);
    }
}
