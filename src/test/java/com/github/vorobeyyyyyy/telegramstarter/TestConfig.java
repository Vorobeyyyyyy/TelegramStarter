package com.github.vorobeyyyyyy.telegramstarter;

import com.pengrad.telegrambot.TelegramBot;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.github.vorobeyyyyyy.telegramstarter")
public class TestConfig {
    @MockBean
    TelegramBot telegramBot;
}
