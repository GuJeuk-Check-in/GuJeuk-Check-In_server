package com.example.burserker.gujeuk.domain.organ.exception;

import com.example.burserker.gujeuk.global.error.exception.ErrorCode;
import com.example.burserker.gujeuk.global.error.exception.GujeukException;

public class OrganAlreadyExistException extends GujeukException {

    public static final GujeukException EXCEPTION = new OrganAlreadyExistException();

    private OrganAlreadyExistException() {
        super(ErrorCode.ORGAN_ALREADY_EXIST);
    }
}
