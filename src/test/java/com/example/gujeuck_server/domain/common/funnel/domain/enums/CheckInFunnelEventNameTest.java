package com.example.gujeuck_server.domain.common.funnel.domain.enums;

import com.example.gujeuck_server.domain.common.funnel.exception.InvalidCheckInFunnelEventNameException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CheckInFunnelEventNameTest {

    @Test
    void 존재하지_않는_eventName이면_전용_예외를_던진다() {
        assertThatThrownBy(() -> CheckInFunnelEventName.from("unknown_event"))
                .isSameAs(InvalidCheckInFunnelEventNameException.EXCEPTION);
    }
}
