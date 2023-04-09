package com.vorobey.telegramstarter.service;

import java.lang.reflect.Parameter;
import java.util.List;
import java.util.Objects;

import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.vorobey.telegramstarter.model.HandlersHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UpdateDispatcher {

    private final HandlersHolder handlersHolder;
    private final List<ParamResolver> paramResolvers;

    public int handleUpdates(List<Update> updates) {
        updates.forEach(update -> {
            try {
                handleUpdate(update);
            } catch (RuntimeException exception) {
                log.error("Error during handling update", exception);
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    public void handleUpdate(Update update) {
        var handler = handlersHolder.getHandlerMethodWithMaxPriority(update);
        var handlerMethod = handler.method();
        Object[] params = new Object[handlerMethod.getParameterCount()];
        Parameter[] parameters = handlerMethod.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            int finalI = i;
            params[i] = paramResolvers.stream()
                    .map(paramResolver -> paramResolver.resolve(handlerMethod, parameters[finalI], update))
                    .filter(Objects::nonNull)
                    .findAny().orElse(null);
        }
        handler.invoke(params);
    }
}
