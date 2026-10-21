package com.example.burserker.gujeuk.domain.organ.exception;

import com.example.burserker.gujeuk.global.error.exception.ErrorCode;
import com.example.burserker.gujeuk.global.error.exception.GujeukException;

public class OrganNotFoundException extends GujeukException {
    public static final GujeukException EXCEPTION = new OrganNotFoundException();

    private OrganNotFoundException() {
        super(ErrorCode.ORGAN_NOT_FOUND);
    }
}
