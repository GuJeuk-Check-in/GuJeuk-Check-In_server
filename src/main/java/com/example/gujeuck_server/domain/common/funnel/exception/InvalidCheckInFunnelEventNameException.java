package com.example.gujeuck_server.domain.common.funnel.exception;

import com.example.gujeuck_server.global.error.exception.ErrorCode;
import com.example.gujeuck_server.global.error.exception.GujeukException;

public class InvalidCheckInFunnelEventNameException extends GujeukException {
    public static final GujeukException EXCEPTION = new InvalidCheckInFunnelEventNameException();

    private InvalidCheckInFunnelEventNameException() {
        super(ErrorCode.INVALID_CHECK_IN_FUNNEL_EVENT_NAME);
    }
}
