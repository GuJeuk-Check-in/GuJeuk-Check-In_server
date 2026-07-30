package com.gujeuk.monitorbot.docker;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.InspectContainerResponse;
import com.github.dockerjava.api.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DockerStatusService {

    private final DockerClient dockerClient;

    public ContainerStatus inspect(String containerName) {
        try {
            InspectContainerResponse response = dockerClient.inspectContainerCmd(containerName).exec();
            return new ContainerStatus(containerName, resolveState(response), resolveHealth(response));
        } catch (NotFoundException e) {
            return new ContainerStatus(containerName, ContainerState.NOT_FOUND, HealthState.NOT_FOUND);
        } catch (Exception e) {
            log.warn("컨테이너 상태 조회 실패: {}", containerName, e);
            return new ContainerStatus(containerName, ContainerState.NOT_FOUND, HealthState.NOT_FOUND);
        }
    }

    private ContainerState resolveState(InspectContainerResponse response) {
        String status = response.getState() != null ? response.getState().getStatus() : null;

        if (status == null) {
            return ContainerState.NOT_FOUND;
        }

        return switch (status) {
            case "running" -> ContainerState.RUNNING;
            case "restarting" -> ContainerState.RESTARTING;
            default -> ContainerState.STOPPED;
        };
    }

    private HealthState resolveHealth(InspectContainerResponse response) {
        if (response.getState() == null || response.getState().getHealth() == null) {
            return HealthState.NOT_CONFIGURED;
        }

        String healthStatus = response.getState().getHealth().getStatus();

        if (healthStatus == null) {
            return HealthState.NOT_CONFIGURED;
        }

        return switch (healthStatus) {
            case "healthy" -> HealthState.HEALTHY;
            case "unhealthy" -> HealthState.UNHEALTHY;
            default -> HealthState.NOT_CONFIGURED;
        };
    }
}
