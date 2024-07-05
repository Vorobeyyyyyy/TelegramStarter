package com.github.vorobeyyyyyy.telegramstarter.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface BotController {
    @AliasFor(
            annotation = Component.class
    )
    String value() default "";
}
