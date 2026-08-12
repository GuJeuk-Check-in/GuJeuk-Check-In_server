package com.example.gujeuck_server.domain.log.exception;

import com.example.gujeuck_server.global.error.exception.ErrorCode;
import com.example.gujeuck_server.global.error.exception.GujeukException;

public class ClientRecordIdMismatchException extends GujeukException {
    public static final GujeukException EXCEPTION = new ClientRecordIdMismatchException();

    private ClientRecordIdMismatchException() {
        super(ErrorCode.CLIENT_RECORD_ID_MISMATCH, "이미 처리된 clientRecordId의 요청 데이터와 현재 요청 데이터가 일치하지 않습니다.");
    }
}
