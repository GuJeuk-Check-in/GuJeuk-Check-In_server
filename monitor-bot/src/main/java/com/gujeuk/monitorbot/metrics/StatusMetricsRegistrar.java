package com.gujeuk.monitorbot.metrics;

import com.gujeuk.monitorbot.config.MonitorProperties;
import com.gujeuk.monitorbot.docker.ContainerState;
import com.gujeuk.monitorbot.docker.HealthState;
import com.gujeuk.monitorbot.probe.EndpointProbe;
import com.gujeuk.monitorbot.probe.HttpProbe;
import com.gujeuk.monitorbot.probe.TlsCertificateProbe;
import com.gujeuk.monitorbot.status.StatusService;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

/**
 * 값 규칙: 정상 1 / 비정상 0 / 확인 불가 -1.
 * 매 Prometheus 스크레이프마다 Docker Engine API와 대상 URL을 즉시 조회한다(TLS 인증서만 30분 캐시).
 * 임계치 판단과 알림은 Grafana 알림 규칙이 담당한다.
 */
@Component
@RequiredArgsConstructor
public class StatusMetricsRegistrar {

    private static final double UNKNOWN = -1;

    private final MeterRegistry meterRegistry;
    private final StatusService statusService;
    private final MonitorProperties properties;
    private final EndpointProbe endpointProbe;
    private final TlsCertificateProbe tlsCertificateProbe;

    @PostConstruct
    public void registerGauges() {
        registerInfrastructureGauges();
        registerContainerGauges();
        registerTargetGauges();
        registerExternalGauges();
    }

    private void registerInfrastructureGauges() {
        Gauge.builder("ec2_online", statusService, s -> s.isEc2Online() ? 1 : 0)
                .description("EC2 인스턴스 온라인 여부 (1=온라인)")
                .register(meterRegistry);
    }

    private void registerContainerGauges() {
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

            // 누적 재시작 횟수. 짧은 시간에 증가하면 크래시 루프다.
            Gauge.builder("docker_container_restart_count",
                            statusService,
                            s -> s.containerStatus(containerName).restartCount())
                    .description("Docker 컨테이너 누적 재시작 횟수")
                    .tag("container", containerName)
                    .register(meterRegistry);

            Gauge.builder("docker_container_oom_killed",
                            statusService,
                            s -> s.containerStatus(containerName).oomKilled() ? 1 : 0)
                    .description("마지막 종료가 OOM 이었는지 (1=OOM). 메모리 부족을 일반 재시작과 구분한다")
                    .tag("container", containerName)
                    .register(meterRegistry);
        }
    }

    private void registerTargetGauges() {
        for (MonitorProperties.Target target : properties.targets()) {
            Gauge.builder("spring_api_up",
                            statusService,
                            s -> s.isSpringApiUp(target) ? 1 : 0)
                    .description("앱 헬스체크 API 응답 여부 (1=UP). 앱 기동과 DB 커넥션을 함께 검사한다")
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

    /**
     * 내부 헬스체크가 통과해도 사용자는 못 쓰는 구간(DNS·Caddy·인증서)을 잡기 위한 외부 관점 지표.
     */
    private void registerExternalGauges() {
        registerProbeGauge("public_endpoint_up",
                "외부 도메인 접근 가능 여부 (1=OK). DNS·프록시·TLS 경로를 포함한다",
                endpointProbe::publicHealth,
                result -> result.isSuccess() ? 1 : 0);

        registerProbeGauge("public_endpoint_latency_ms",
                "외부 도메인 응답 시간(ms). 실패 시 -1",
                endpointProbe::publicHealth,
                result -> result.isSuccess() ? result.latencyMs() : UNKNOWN);

        registerProbeGauge("public_api_up",
                "외부에서 실제 조회 API 호출 성공 여부 (1=OK). 라우팅·시큐리티 설정 오류를 잡는다",
                endpointProbe::publicApi,
                result -> result.isSuccess() ? 1 : 0);

        Gauge.builder("tls_cert_days_remaining", tlsCertificateProbe, TlsCertificateProbe::daysRemaining)
                .description("TLS 인증서 만료까지 남은 일수. 확인 불가 시 -1")
                .register(meterRegistry);
    }

    private void registerProbeGauge(
            String name,
            String description,
            Supplier<HttpProbe.Result> probe,
            java.util.function.ToDoubleFunction<HttpProbe.Result> valueOf
    ) {
        Gauge.builder(name, probe, p -> {
                    HttpProbe.Result result = p.get();
                    return result == null ? UNKNOWN : valueOf.applyAsDouble(result);
                })
                .description(description)
                .register(meterRegistry);
    }

    private double runningValue(ContainerState state) {
        return state == ContainerState.RUNNING ? 1 : 0;
    }

    private double healthyValue(HealthState health) {
        return switch (health) {
            case HEALTHY -> 1;
            case NOT_CONFIGURED -> UNKNOWN;
            case UNHEALTHY, NOT_FOUND -> 0;
        };
    }
}
