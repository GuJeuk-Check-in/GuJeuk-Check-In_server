package com.gujeuk.monitorbot.metrics;

import com.gujeuk.monitorbot.config.MonitorProperties;
import com.gujeuk.monitorbot.docker.ContainerState;
import com.gujeuk.monitorbot.docker.HealthState;
import com.gujeuk.monitorbot.status.StatusService;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 값 규칙: 정상 1 / 비정상 0 / Healthcheck 미설정 -1.
 * 매 Prometheus 스크레이프마다 Docker Engine API와 헬스체크 URL을 즉시 조회한다(값을 캐시하지 않음).
 */
@Component
@RequiredArgsConstructor
public class StatusMetricsRegistrar {

    private final MeterRegistry meterRegistry;
    private final StatusService statusService;
    private final MonitorProperties properties;

    @PostConstruct
    public void registerGauges() {
        Gauge.builder("ec2_online", statusService, s -> s.isEc2Online() ? 1 : 0)
                .description("EC2 인스턴스 온라인 여부 (1=온라인)")
                .register(meterRegistry);

        for (String containerName : statusService.allTrackedContainers()) {
            Gauge.builder("docker_container_running",
                            statusService,
                            s -> runningValue(s.containerStatus(containerName).state()))
                    .description("Docker 컨테이너 실행 상태 (1=RUNNING, 0=STOPPED/RESTARTING)")
                    .tag("container", containerName)
                    .register(meterRegistry);

            Gauge.builder("docker_container_healthy",
                            statusService,
                            s -> healthyValue(s.containerStatus(containerName).health()))
                    .description("Docker Healthcheck 상태 (1=HEALTHY, 0=UNHEALTHY, -1=NOT_CONFIGURED)")
                    .tag("container", containerName)
                    .register(meterRegistry);
        }

        for (MonitorProperties.Target target : properties.targets()) {
            Gauge.builder("spring_api_up",
                            statusService,
                            s -> s.isSpringApiUp(target) ? 1 : 0)
                    .description("애플리케이션 헬스체크 API 응답 여부 (1=UP)")
                    .tag("target", target.name())
                    .register(meterRegistry);

            Gauge.builder("database_up",
                            statusService,
                            s -> healthyValue(s.databaseStatus(target).health()))
                    .description("DB 컨테이너 Healthcheck 상태 (1=UP, 0=DOWN, -1=NOT_CONFIGURED)")
                    .tag("target", target.name())
                    .register(meterRegistry);
        }
    }

    private double runningValue(ContainerState state) {
        return state == ContainerState.RUNNING ? 1 : 0;
    }

    private double healthyValue(HealthState health) {
        return switch (health) {
            case HEALTHY -> 1;
            case NOT_CONFIGURED -> -1;
            case UNHEALTHY, NOT_FOUND -> 0;
        };
    }
}
