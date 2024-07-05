package com.github.vorobeyyyyyy.telegramstarter.config;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

import com.github.vorobeyyyyyy.telegramstarter.annotation.BotController;
import com.github.vorobeyyyyyy.telegramstarter.model.HandlersHolder;
import com.github.vorobeyyyyyy.telegramstarter.model.UpdateHandler;
import com.github.vorobeyyyyyy.telegramstarter.service.UpdateMatcher;
import com.pengrad.telegrambot.model.Update;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ReflectionUtils;

@Configuration
@Slf4j
public class HandlersConfig {

    @Bean
    public <T extends Annotation> HandlersHolder handlersHolder(
            ApplicationContext applicationContext,
            List<UpdateMatcher<T>> matchers
    ) {
        log.info("Configuring Telegram Bot handlers");
        Collection<Object> controllers = applicationContext.getBeansWithAnnotation(BotController.class).values();

        Map<Function<Update, Integer>, UpdateHandler> handlers = new HashMap<>();
        for (UpdateMatcher<T> matcher : matchers) {
            for (Object controller : controllers) {
                for (Method method : ReflectionUtils.getDeclaredMethods(controller.getClass())) {
                    T annotation = method.getAnnotation(matcher.getAnnotationClass());
                    if (Objects.nonNull(annotation)) {
                        handlers.put(update -> matcher.matches(update, annotation),
                                new UpdateHandler(method, controller, annotation));
                    }
                }
            }
        }

        log.info("Found {} handles", handlers.size());
        return new HandlersHolder(handlers);
    }
}
