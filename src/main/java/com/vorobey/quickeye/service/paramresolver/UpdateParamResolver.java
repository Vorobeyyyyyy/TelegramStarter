package com.vorobey.quickeye.service.paramresolver;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import com.pengrad.telegrambot.model.Update;
import com.vorobey.quickeye.service.ParamResolver;
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
