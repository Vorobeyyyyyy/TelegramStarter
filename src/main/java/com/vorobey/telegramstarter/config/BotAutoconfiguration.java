package com.vorobey.telegramstarter.config;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.GetUpdates;
import com.vorobey.telegramstarter.config.property.BotProperty;
import com.vorobey.telegramstarter.service.UpdateDispatcher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class BotAutoconfiguration {
    private final BotProperty botProperty;
    private final UpdateDispatcher updateDispatcher;

    @Bean
    public TelegramBot bot() {
        TelegramBot telegramBot = new TelegramBot(botProperty.getToken());
        telegramBot.setUpdatesListener(updateDispatcher::handleUpdates, new GetUpdates().limit(1));
        return telegramBot;
    }
}
