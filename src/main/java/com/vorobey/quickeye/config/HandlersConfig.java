package com.vorobey.quickeye.config;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

import com.pengrad.telegrambot.model.Update;
import com.vorobey.quickeye.annotation.BotController;
import com.vorobey.quickeye.model.UpdateHandler;
import com.vorobey.quickeye.model.HandlersHolder;
import com.vorobey.quickeye.service.UpdateMatcher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ReflectionUtils;

@Configuration
public class HandlersConfig {

    @Bean
    public <T extends Annotation> HandlersHolder handlersHolder(
            ApplicationContext applicationContext,
            List<UpdateMatcher<T>> matchers
    ) {
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
        return new HandlersHolder(handlers);
    }
}
