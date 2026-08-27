package com.example.gujeuck_server.domain.log.exception;

import com.example.gujeuck_server.global.error.exception.ErrorCode;
import com.example.gujeuck_server.global.error.exception.GujeukException;

public class ClientRecordIdMismatchException extends GujeukException {
    public static final GujeukException EXCEPTION = new ClientRecordIdMismatchException();

    private ClientRecordIdMismatchException() {
        super(ErrorCode.CLIENT_RECORD_ID_MISMATCH);
    }
}
