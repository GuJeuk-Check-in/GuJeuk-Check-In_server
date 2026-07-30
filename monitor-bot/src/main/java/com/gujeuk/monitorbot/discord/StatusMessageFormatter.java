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

        EmbedBuilder embed = new EmbedBuilder()
                .setTitle(title)
                .setColor(healthy ? COLOR_HEALTHY : COLOR_DEGRADED)
                .setDescription("**전체 상태**  " + (healthy ? "정상" : "장애 발생") + "\n**EC2**  " + badge(snapshot.ec2Online()))
                .setFooter("구즉 모니터링 · monitor-bot")
                .setTimestamp(Instant.now());

        for (StatusSnapshot.TargetStatus target : snapshot.targets()) {
            embed.addField(
                    targetHeader(target.name()),
                    "Spring API : " + badge(target.springApiUp()) + "\n"
                            + "App : " + containerLine(target.app()) + "\n"
                            + "DB : " + containerLine(target.database()) + "\n"
                            + "Redis : " + containerLine(target.redis()),
                    true
            );
        }

        if (!snapshot.extraContainers().isEmpty()) {
            StringBuilder extra = new StringBuilder();
            for (ContainerStatus status : snapshot.extraContainers()) {
                extra.append("`").append(status.containerName()).append("` : ")
                        .append(containerLine(status)).append("\n");
            }
            embed.addField("기타 컨테이너", extra.toString(), false);
        }

        return embed.build();
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
        return up ? "정상 (UP)" : "장애 (DOWN)";
    }

    private String containerLine(ContainerStatus status) {
        String health = switch (status.health()) {
            case HEALTHY -> "HEALTHY";
            case UNHEALTHY -> "UNHEALTHY";
            case NOT_CONFIGURED -> "-";
            case NOT_FOUND -> "NOT_FOUND";
        };

        return status.state() + " (" + health + ")";
    }
}
