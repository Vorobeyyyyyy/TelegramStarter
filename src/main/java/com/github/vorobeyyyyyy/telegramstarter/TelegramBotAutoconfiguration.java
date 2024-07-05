package com.github.vorobeyyyyyy.telegramstarter;

import com.github.vorobeyyyyyy.telegramstarter.config.property.BotProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@AutoConfiguration
@EnableConfigurationProperties(BotProperties.class)
public class TelegramBotAutoconfiguration {
}
