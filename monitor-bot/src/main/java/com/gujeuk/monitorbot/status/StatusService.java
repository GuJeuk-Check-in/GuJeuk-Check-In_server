package com.gujeuk.monitorbot.status;

import com.gujeuk.monitorbot.config.MonitorProperties;
import com.gujeuk.monitorbot.docker.ContainerStatus;
import com.gujeuk.monitorbot.docker.DockerStatusService;
import com.gujeuk.monitorbot.health.HealthCheckService;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatusService {

    private final DockerStatusService dockerStatusService;
    private final HealthCheckService healthCheckService;
    private final MonitorProperties properties;

    public boolean isEc2Online() {
        // 이 프로세스 자체가 EC2 위에서 응답하고 있다는 것 자체가 온라인 신호다.
        return true;
    }

    public ContainerStatus containerStatus(String containerName) {
        return dockerStatusService.inspect(containerName);
    }

    public List<String> allTrackedContainers() {
        List<String> containers = new ArrayList<>();
        for (MonitorProperties.Target target : properties.targets()) {
            containers.add(target.appContainer());
            containers.add(target.dbContainer());
            containers.add(target.redisContainer());
        }
        if (properties.extraContainers() != null) {
            containers.addAll(properties.extraContainers());
        }
        return containers;
    }

    public boolean isSpringApiUp(MonitorProperties.Target target) {
        return healthCheckService.isUp(target.healthCheckUrl(), properties.healthCheckTimeoutSeconds());
    }

    public ContainerStatus databaseStatus(MonitorProperties.Target target) {
        return dockerStatusService.inspect(target.dbContainer());
    }

    public StatusSnapshot snapshot() {
        List<StatusSnapshot.TargetStatus> targetStatuses = new ArrayList<>();

        for (MonitorProperties.Target target : properties.targets()) {
            targetStatuses.add(new StatusSnapshot.TargetStatus(
                    target.name(),
                    isSpringApiUp(target),
                    dockerStatusService.inspect(target.appContainer()),
                    dockerStatusService.inspect(target.dbContainer()),
                    dockerStatusService.inspect(target.redisContainer())
            ));
        }

        List<ContainerStatus> extra = new ArrayList<>();
        if (properties.extraContainers() != null) {
            for (String containerName : properties.extraContainers()) {
                extra.add(dockerStatusService.inspect(containerName));
            }
        }

        return new StatusSnapshot(isEc2Online(), targetStatuses, extra);
    }
}
