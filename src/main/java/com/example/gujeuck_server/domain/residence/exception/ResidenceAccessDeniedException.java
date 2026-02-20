package com.example.gujeuck_server.domain.residence.exception;

import com.example.gujeuck_server.global.error.exception.ErrorCode;
import com.example.gujeuck_server.global.error.exception.GujeukException;

public class ResidenceAccessDeniedException extends GujeukException {
    public static final GujeukException EXCEPTION = new ResidenceAccessDeniedException();

    private ResidenceAccessDeniedException() {
        super(ErrorCode.RESIDENCE_ACCESS_DENIED);
    }
}
