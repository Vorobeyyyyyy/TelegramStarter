package com.github.vorobeyyyyyy.telegramstarter.service.paramresolver.resolver;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import com.github.vorobeyyyyyy.telegramstarter.annotation.params.ChatId;
import com.github.vorobeyyyyyy.telegramstarter.service.ParamResolver;
import com.github.vorobeyyyyyy.telegramstarter.util.UpdateUtils;
import com.pengrad.telegrambot.model.Update;
import org.springframework.stereotype.Component;
import org.springframework.util.TypeUtils;

import static java.util.Objects.nonNull;

@Component
public class ChatIdParamResolver implements ParamResolver {

    @Override
    public Object resolve(Method method, Parameter parameter, Update update) {
        return UpdateUtils.getChatId(update)
                .filter(ignored -> nonNull(parameter.getAnnotation(ChatId.class)))
                .filter(ignored -> TypeUtils.isAssignable(Long.class, parameter.getType()))
                .orElse(null);
    }
}
