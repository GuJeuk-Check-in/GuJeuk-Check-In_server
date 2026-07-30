package com.gujeuk.monitorbot.discord;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 운영 확인용: 1시간 주기 정기 보고를 스케줄과 무관하게 즉시 한 번 발송한다.
 * 외부에 노출되지 않는 내부 포트(127.0.0.1:8090)에서만 접근 가능하다.
 */
@RestController
@RequestMapping("/internal")
@RequiredArgsConstructor
@ConditionalOnBean(ScheduledReportService.class)
public class ReportTriggerController {

    private final ScheduledReportService scheduledReportService;

    @PostMapping("/report/trigger")
    public String trigger() {
        scheduledReportService.sendPeriodicReport();
        return "sent";
    }
}
