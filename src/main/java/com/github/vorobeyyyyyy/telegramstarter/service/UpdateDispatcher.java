package com.github.vorobeyyyyyy.telegramstarter.service;

import java.util.List;

import com.github.vorobeyyyyyy.telegramstarter.model.HandlersHolder;
import com.github.vorobeyyyyyy.telegramstarter.service.paramresolver.ParamResolverManager;
import com.github.vorobeyyyyyy.telegramstarter.service.resultresolver.ResultResolverManager;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UpdateDispatcher {

    private final HandlersHolder handlersHolder;
    private final ParamResolverManager paramResolverManager;
    private final ResultResolverManager resultResolverManager;

    public int handleUpdates(List<Update> updates) {
        updates.forEach(update -> {
            try {
                resolveUpdate(update);
            } catch (RuntimeException exception) {
                log.error("Error during handling update", exception);
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    public void resolveUpdate(Update update) {
        var handler = handlersHolder.getHandlerMethodWithMaxPriority(update);
        var params = paramResolverManager.getParams(handler.method(), update);
        var response = handler.invoke(params);
        resultResolverManager.resolveResponse(update, response);
    }
}
