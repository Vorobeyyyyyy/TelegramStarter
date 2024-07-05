package com.github.vorobeyyyyyy.telegramstarter.model;


import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import com.github.vorobeyyyyyy.telegramstarter.service.UpdateMatcher;
import com.pengrad.telegrambot.model.Update;
import org.apache.commons.lang3.tuple.Pair;

public record HandlersHolder(Map<Function<Update, Integer>, UpdateHandler> handlers) {

    public UpdateHandler getHandlerMethodWithMaxPriority(Update update) {

        var handlersWithPriority = handlers.entrySet().stream()
                .map(entry -> Pair.of(entry.getKey().apply(update), entry.getValue()))
                .toList();

        int maxPriority = handlersWithPriority.stream()
                .mapToInt(Pair::getKey)
                .max()
                .orElse(UpdateMatcher.NOT_MATCH_PRIORITY);

        if (maxPriority == UpdateMatcher.NOT_MATCH_PRIORITY) {
            throw new RuntimeException("Not found suitable method");
        }

        List<UpdateHandler> methods = handlersWithPriority.stream()
                .filter(pair -> pair.getKey() == maxPriority)
                .map(Pair::getValue).toList();

        if (methods.size() > 1) {
            throw new RuntimeException("Found more than two matchers with same priority [%n%s%n]"
                    .formatted(String.join(System.lineSeparator(),
                            methods.stream().map(UpdateHandler::method).map(Method::toString).toList())));
        }

        return methods.get(0);
    }
}
