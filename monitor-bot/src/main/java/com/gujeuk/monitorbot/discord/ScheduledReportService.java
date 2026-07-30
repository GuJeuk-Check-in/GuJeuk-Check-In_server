package com.gujeuk.monitorbot.discord;

import com.gujeuk.monitorbot.config.MonitorProperties;
import com.gujeuk.monitorbot.status.StatusService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "monitor.discord", name = "bot-token")
public class ScheduledReportService {

    private final JDA jda;
    private final MonitorProperties properties;
    private final StatusService statusService;
    private final StatusMessageFormatter statusMessageFormatter;

    @Scheduled(cron = "${monitor.report-cron}")
    public void sendPeriodicReport() {
        String channelId = properties.discord().channelId();
        if (channelId == null || channelId.isBlank()) {
            log.warn("DISCORD_CHANNEL_ID가 설정되지 않아 정기 보고를 건너뜁니다.");
            return;
        }

        TextChannel channel = jda.getTextChannelById(channelId);
        if (channel == null) {
            log.warn("채널({})을 찾을 수 없어 정기 보고를 건너뜁니다.", channelId);
            return;
        }

        var embed = statusMessageFormatter.buildEmbed(statusService.snapshot(), "구즉 정기 보고");
        channel.sendMessageEmbeds(embed).queue();
    }
}
