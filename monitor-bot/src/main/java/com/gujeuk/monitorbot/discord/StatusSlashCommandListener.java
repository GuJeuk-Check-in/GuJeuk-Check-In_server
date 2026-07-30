package com.gujeuk.monitorbot.discord;

import com.gujeuk.monitorbot.status.StatusService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class StatusSlashCommandListener extends ListenerAdapter {

    private final StatusService statusService;
    private final StatusMessageFormatter statusMessageFormatter;

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (!"서버상태".equals(event.getName())) {
            return;
        }

        event.deferReply().queue();

        try {
            String message = statusMessageFormatter.format(statusService.snapshot());
            event.getHook().sendMessage(message).queue();
        } catch (Exception e) {
            log.error("/서버상태 처리 중 오류", e);
            event.getHook().sendMessage("상태 조회 중 오류가 발생했습니다.").queue();
        }
    }
}
