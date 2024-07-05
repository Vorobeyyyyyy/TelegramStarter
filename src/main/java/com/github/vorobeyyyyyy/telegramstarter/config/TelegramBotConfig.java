package com.github.vorobeyyyyyy.telegramstarter.config;

import java.util.Objects;

import com.github.vorobeyyyyyy.telegramstarter.config.property.BotProperties;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.GetMe;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class TelegramBotConfig {
    private final BotProperties botProperties;

    @Bean
    public TelegramBot bot() {
        log.info("Configuring Telegram Bot");
        if (Objects.isNull(botProperties.getToken())) {
            throw new Error("Bot token can't be NULL");
        }
        TelegramBot telegramBot = new TelegramBot(botProperties.getToken());
        if (!telegramBot.execute(new GetMe()).isOk()) {
            throw new Error("Can't execute GetMe, probably invalid token");
        }
        return telegramBot;
    }
}
