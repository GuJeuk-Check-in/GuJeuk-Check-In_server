package com.gujeuk.monitorbot.discord;

import com.gujeuk.monitorbot.docker.ContainerStatus;
import com.gujeuk.monitorbot.status.StatusSnapshot;
import org.springframework.stereotype.Component;

@Component
public class StatusMessageFormatter {

    public String format(StatusSnapshot snapshot) {
        StringBuilder sb = new StringBuilder();

        sb.append("**구즉 서버 상태**\n");
        sb.append("EC2: ").append(snapshot.ec2Online() ? "🟢 ONLINE" : "🔴 OFFLINE").append("\n\n");

        for (StatusSnapshot.TargetStatus target : snapshot.targets()) {
            sb.append("**[").append(target.name().toUpperCase()).append("]**\n");
            sb.append("- Spring API: ").append(target.springApiUp() ? "🟢 UP" : "🔴 DOWN").append("\n");
            sb.append("- App 컨테이너: ").append(containerLine(target.app())).append("\n");
            sb.append("- DB: ").append(containerLine(target.database())).append("\n");
            sb.append("- Redis: ").append(containerLine(target.redis())).append("\n\n");
        }

        if (!snapshot.extraContainers().isEmpty()) {
            sb.append("**[기타 컨테이너]**\n");
            for (ContainerStatus status : snapshot.extraContainers()) {
                sb.append("- ").append(status.containerName()).append(": ").append(containerLine(status)).append("\n");
            }
        }

        return sb.toString();
    }

    private String containerLine(ContainerStatus status) {
        String state = switch (status.state()) {
            case RUNNING -> "🟢 RUNNING";
            case RESTARTING -> "🟡 RESTARTING";
            case STOPPED -> "🔴 STOPPED";
            case NOT_FOUND -> "⚫ NOT_FOUND";
        };

        String health = switch (status.health()) {
            case HEALTHY -> "HEALTHY";
            case UNHEALTHY -> "UNHEALTHY";
            case NOT_CONFIGURED -> "NOT_CONFIGURED";
            case NOT_FOUND -> "NOT_FOUND";
        };

        return state + " / " + health;
    }
}
