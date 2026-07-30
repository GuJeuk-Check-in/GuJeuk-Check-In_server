package com.gujeuk.monitorbot.status;

import com.gujeuk.monitorbot.docker.ContainerStatus;
import java.util.List;

public record StatusSnapshot(
        boolean ec2Online,
        List<TargetStatus> targets,
        List<ContainerStatus> extraContainers
) {

    public record TargetStatus(
            String name,
            boolean springApiUp,
            ContainerStatus app,
            ContainerStatus database,
            ContainerStatus redis
    ) {
    }
}
