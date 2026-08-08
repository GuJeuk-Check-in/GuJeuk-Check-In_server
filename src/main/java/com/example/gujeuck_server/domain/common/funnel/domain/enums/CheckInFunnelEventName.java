package com.example.gujeuck_server.domain.common.funnel.domain.enums;

import com.example.gujeuck_server.domain.common.funnel.exception.InvalidCheckInFunnelEventNameException;
import com.fasterxml.jackson.annotation.JsonCreator;

public enum CheckInFunnelEventName {
    CHECK_IN_PAGE_VIEW("check_in_page_view"),
    PHONE_INPUT_STARTED("phone_input_started"),
    USER_CHECK_SUBMITTED("user_check_submitted"),
    USER_CHECK_SUCCEEDED("user_check_succeeded"),
    USER_CHECK_FAILED("user_check_failed"),
    CHECK_IN_FORM_VIEW("check_in_form_view"),
    PURPOSE_SELECTED("purpose_selected"),
    CHECK_IN_SUBMITTED("check_in_submitted"),
    CHECK_IN_API_SUCCEEDED("check_in_api_succeeded"),
    CHECK_IN_API_FAILED("check_in_api_failed"),
    CHECK_IN_COMPLETED_VIEW("check_in_completed_view"),
    CHECK_IN_ABANDONED("check_in_abandoned");

    private final String value;

    CheckInFunnelEventName(String value) {
        this.value = value;
    }

    @JsonCreator
    public static CheckInFunnelEventName from(String value) {
        for (CheckInFunnelEventName eventName : values()) {
            if (eventName.value.equals(value)) {
                return eventName;
            }
        }

        throw InvalidCheckInFunnelEventNameException.EXCEPTION;
    }

    public String value() {
        return value;
    }
}
