package com.example.burserker.gujeuk.domain.residence.exception;

import com.example.burserker.gujeuk.global.error.exception.ErrorCode;
import com.example.burserker.gujeuk.global.error.exception.GujeukException;

public class ResidenceAlreadyException extends GujeukException {
    public static final GujeukException EXCEPTION = new ResidenceAlreadyException();

    private ResidenceAlreadyException() {
        super(ErrorCode.RESIDENCE_ALREADY_EXIST);
    }
}
