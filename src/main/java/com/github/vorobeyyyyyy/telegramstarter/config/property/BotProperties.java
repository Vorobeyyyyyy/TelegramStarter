package com.github.vorobeyyyyyy.telegramstarter.config.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "telegrambot")
@Data
public class BotProperties {

    private String token;
}
