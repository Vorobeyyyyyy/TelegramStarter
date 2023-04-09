package com.vorobey.telegramstarter.service.paramresolver;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Optional;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.vorobey.telegramstarter.annotation.params.ChatId;
import com.vorobey.telegramstarter.service.ParamResolver;
import org.springframework.stereotype.Component;
import org.springframework.util.TypeUtils;

import static java.util.Objects.nonNull;

@Component
public class ChatIdParamResolver implements ParamResolver {

    @Override
    public Object resolve(Method method, Parameter parameter, Update update) {
        return Optional.of(update)
                .map(Update::message)
                .map(Message::chat)
                .map(Chat::id)
                .filter(ignored -> nonNull(parameter.getAnnotation(ChatId.class)))
                .filter(ignored -> TypeUtils.isAssignable(Long.class, parameter.getType()))
                .orElse(null);
    }
}
