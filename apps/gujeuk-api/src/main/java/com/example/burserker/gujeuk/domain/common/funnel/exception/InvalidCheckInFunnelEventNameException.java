package com.example.burserker.gujeuk.domain.common.funnel.exception;

import com.example.burserker.gujeuk.global.error.exception.ErrorCode;
import com.example.burserker.gujeuk.global.error.exception.GujeukException;

public class InvalidCheckInFunnelEventNameException extends GujeukException {
    public static final GujeukException EXCEPTION = new InvalidCheckInFunnelEventNameException();

    private InvalidCheckInFunnelEventNameException() {
        super(ErrorCode.INVALID_CHECK_IN_FUNNEL_EVENT_NAME);
    }
}
