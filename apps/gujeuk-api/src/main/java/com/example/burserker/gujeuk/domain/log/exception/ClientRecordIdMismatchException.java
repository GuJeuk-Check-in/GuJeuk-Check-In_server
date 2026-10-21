package com.example.burserker.gujeuk.domain.log.exception;

import com.example.burserker.gujeuk.global.error.exception.ErrorCode;
import com.example.burserker.gujeuk.global.error.exception.GujeukException;

public class ClientRecordIdMismatchException extends GujeukException {
    public static final GujeukException EXCEPTION = new ClientRecordIdMismatchException();

    private ClientRecordIdMismatchException() {
        super(ErrorCode.CLIENT_RECORD_ID_MISMATCH);
    }
}
