package com.github.vorobeyyyyyy.telegramstarter.service.paramresolver;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;
import java.util.Objects;

import com.github.vorobeyyyyyy.telegramstarter.service.ParamResolver;
import com.pengrad.telegrambot.model.Update;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ParamResolverManager {

    private final List<ParamResolver> paramResolvers;

    public Object[] getParams(Method handlerMethod, Update update) {
        var params = new Object[handlerMethod.getParameterCount()];
        Parameter[] parameters = handlerMethod.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            int finalI = i;
            params[i] = paramResolvers.stream()
                    .map(paramResolver -> paramResolver.resolve(handlerMethod, parameters[finalI], update))
                    .filter(Objects::nonNull)
                    .findAny().orElse(null);
        }
        return params;
    }
}
