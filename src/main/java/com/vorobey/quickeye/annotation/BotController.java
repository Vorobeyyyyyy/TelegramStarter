package com.vorobey.quickeye.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface BotController {
    @AliasFor(
            annotation = Component.class
    )
    String value() default "";
}
