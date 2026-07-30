package com.gujeuk.monitorbot.discord;

import com.gujeuk.monitorbot.docker.ContainerState;
import com.gujeuk.monitorbot.docker.ContainerStatus;
import com.gujeuk.monitorbot.docker.HealthState;
import com.gujeuk.monitorbot.status.StatusSnapshot;
import java.awt.Color;
import java.time.Instant;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.springframework.stereotype.Component;

@Component
public class StatusMessageFormatter {

    private static final Color COLOR_HEALTHY = new Color(0x2ECC71);
    private static final Color COLOR_DEGRADED = new Color(0xE74C3C);

    public MessageEmbed buildEmbed(StatusSnapshot snapshot, String title) {
        boolean healthy = isHealthy(snapshot);
        StringBuilder body = new StringBuilder();

        body.append("# ").append(healthy ? "🟢 " : "🔴 ").append(title).append("\n\n");

        body.append("## EC2\n");
        body.append(badge(snapshot.ec2Online())).append("\n\n");

        for (StatusSnapshot.TargetStatus target : snapshot.targets()) {
            body.append("## ").append(targetHeader(target.name())).append("\n");
            body.append("**Spring API** ").append(badge(target.springApiUp())).append("\n");
            body.append("**App** ").append(containerLine(target.app())).append("\n");
            body.append("**DB** ").append(containerLine(target.database())).append("\n");
            body.append("**Redis** ").append(containerLine(target.redis())).append("\n\n");
        }

        if (!snapshot.extraContainers().isEmpty()) {
            body.append("## 기타 컨테이너\n");
            for (ContainerStatus status : snapshot.extraContainers()) {
                body.append("**").append(status.containerName()).append("** ")
                        .append(containerLine(status)).append("\n");
            }
        }

        return new EmbedBuilder()
                .setColor(healthy ? COLOR_HEALTHY : COLOR_DEGRADED)
                .setDescription(body.toString())
                .setFooter("구즉 모니터링 · monitor-bot")
                .setTimestamp(Instant.now())
                .build();
    }

    private boolean isHealthy(StatusSnapshot snapshot) {
        if (!snapshot.ec2Online()) {
            return false;
        }
        for (StatusSnapshot.TargetStatus target : snapshot.targets()) {
            if (!target.springApiUp()) {
                return false;
            }
            if (!isOk(target.app()) || !isOk(target.database()) || !isOk(target.redis())) {
                return false;
            }
        }
        for (ContainerStatus status : snapshot.extraContainers()) {
            if (!isOk(status)) {
                return false;
            }
        }
        return true;
    }

    private boolean isOk(ContainerStatus status) {
        boolean running = status.state() == ContainerState.RUNNING;
        boolean healthOk = status.health() != HealthState.UNHEALTHY && status.health() != HealthState.NOT_FOUND;
        return running && healthOk;
    }

    private String targetHeader(String name) {
        if ("prod".equalsIgnoreCase(name)) {
            return "PROD";
        }
        if ("stag".equalsIgnoreCase(name)) {
            return "STAG";
        }
        return name.toUpperCase();
    }

    private String badge(boolean up) {
        return up ? "🟢 UP" : "🔴 DOWN";
    }

    private String containerLine(ContainerStatus status) {
        String stateEmoji = switch (status.state()) {
            case RUNNING -> "🟢";
            case RESTARTING -> "🟡";
            case STOPPED -> "🔴";
            case NOT_FOUND -> "⚫";
        };

        String health = switch (status.health()) {
            case HEALTHY -> "HEALTHY";
            case UNHEALTHY -> "UNHEALTHY";
            case NOT_CONFIGURED -> "-";
            case NOT_FOUND -> "NOT_FOUND";
        };

        return stateEmoji + " " + status.state() + " (" + health + ")";
    }
}
