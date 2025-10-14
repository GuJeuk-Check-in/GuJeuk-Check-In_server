package com.example.gujeuck_server.global.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GujeukException extends RuntimeException {
    private final ErrorCode errorCode;

    public GujeukException(ErrorCode errorCode, String customMessage) {
        super(customMessage);
        this.errorCode = errorCode;
    }
}
