package com.github.vorobeyyyyyy.telegramstarter.config;

import com.github.vorobeyyyyyy.telegramstarter.service.UpdateDispatcher;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.GetUpdates;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class UpdateDispatcherConfig {

    private final TelegramBot telegramBot;
    private final UpdateDispatcher updateDispatcher;

    @PostConstruct
    public void configure() {
        telegramBot.setUpdatesListener(
                updateDispatcher::handleUpdates,
                new GetUpdates().limit(1).timeout(20)
        );
    }
}
