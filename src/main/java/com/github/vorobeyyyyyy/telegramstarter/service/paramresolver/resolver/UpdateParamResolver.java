package com.github.vorobeyyyyyy.telegramstarter.service.paramresolver.resolver;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import com.github.vorobeyyyyyy.telegramstarter.service.ParamResolver;
import com.pengrad.telegrambot.model.Update;
import org.springframework.stereotype.Component;

@Component
public class UpdateParamResolver implements ParamResolver {

    @Override
    public Object resolve(Method method, Parameter parameter, Update update) {
        if (Update.class.isAssignableFrom(parameter.getType())) {
            return update;
        }
        return null;
    }
}
