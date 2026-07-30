package com.gujeuk.monitorbot.discord;

import com.gujeuk.monitorbot.config.MonitorProperties;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@ConditionalOnProperty(prefix = "monitor.discord", name = "bot-token")
public class DiscordBotConfig {

    @Bean(destroyMethod = "shutdown")
    public JDA jda(
            MonitorProperties properties,
            StatusSlashCommandListener listener
    ) throws InterruptedException {
        JDA jda = JDABuilder.createLight(properties.discord().botToken())
                .enableIntents(GatewayIntent.GUILD_MESSAGES)
                .addEventListeners(listener)
                .build();

        jda.awaitReady();

        String guildId = properties.discord().guildId();
        if (guildId != null && !guildId.isBlank()) {
            jda.getGuildById(guildId)
                    .updateCommands()
                    .addCommands(Commands.slash("서버상태", "구즉 EC2/컨테이너/API/DB 상태를 조회합니다."))
                    .queue();
            log.info("길드({}) 슬래시 커맨드 등록 완료", guildId);
        } else {
            jda.updateCommands()
                    .addCommands(Commands.slash("서버상태", "구즉 EC2/컨테이너/API/DB 상태를 조회합니다."))
                    .queue();
            log.info("글로벌 슬래시 커맨드 등록 완료 (전파까지 최대 1시간 소요될 수 있음)");
        }

        return jda;
    }
}
