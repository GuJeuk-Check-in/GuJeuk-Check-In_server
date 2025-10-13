package com.example.gujeuck_server.domain.user.exception;

import com.example.gujeuck_server.global.error.exception.ErrorCode;
import com.example.gujeuck_server.global.error.exception.GujeukException;

public class CompanionIdNotFoundException extends GujeukException {

    public CompanionIdNotFoundException(String companionId) {
        super(ErrorCode.COMPANION_NOT_FOUND, "존재하지 않는 동행인 ID: " + companionId);
    }

    public static CompanionIdNotFoundException of(String companionId) {
        return new CompanionIdNotFoundException(companionId);
    }
}