package com.github.vorobeyyyyyy.telegramstarter.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

@Configuration
@Slf4j
public class GeneralConfig {

    @Bean
    public PathMatcher pathMatcher() {
        log.info("Creating PathMatcher for Telegram Bot");
        return new AntPathMatcher();
    }
}
