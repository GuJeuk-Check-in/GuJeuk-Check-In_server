package com.example.gujeuck_server.domain.organ.exception;

import com.example.gujeuck_server.global.error.exception.ErrorCode;
import com.example.gujeuck_server.global.error.exception.GujeukException;

public class OrganAlreadyExistException extends GujeukException {

    public static final GujeukException EXCEPTION = new OrganAlreadyExistException();

    private OrganAlreadyExistException() {
        super(ErrorCode.ORGAN_ALREADY_EXIST);
    }
}
