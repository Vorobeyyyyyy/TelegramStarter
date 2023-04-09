package com.vorobey.telegramstarter.service.paramresolver;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Optional;

import com.pengrad.telegrambot.model.Update;
import com.vorobey.telegramstarter.annotation.params.MessageParam;
import com.vorobey.telegramstarter.annotation.mappings.CommandMapping;
import com.vorobey.telegramstarter.service.ParamResolver;
import com.vorobey.telegramstarter.util.UpdateUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.NumberUtils;
import org.springframework.util.PathMatcher;
import org.springframework.util.TypeUtils;

import static java.util.Objects.isNull;

@Component
@RequiredArgsConstructor
public class MessageParamParamResolver implements ParamResolver {

    private final PathMatcher pathMatcher;

    @Override
    @Nullable
    public Object resolve(@NonNull Method method, Parameter parameter, @NonNull Update update) {
        String text = UpdateUtils.getText(update);
        MessageParam messageParam = parameter.getAnnotation(MessageParam.class);
        CommandMapping commandMapping = method.getAnnotation(CommandMapping.class);

        if (StringUtils.isBlank(text)
                || isNull(commandMapping)
                || isNull(messageParam)
                || !(isNumberParam(parameter.getType()) ||
                parameter.getType().equals(String.class))) {
            return null;
        }
        String paramName = Optional.of(messageParam.value())
                .filter(StringUtils::isNotEmpty)
                .orElse(parameter.getName());

        var templateVariables = pathMatcher.extractUriTemplateVariables(commandMapping.value(), text);
        String rawParam = templateVariables.get(paramName);
        return castToType(rawParam, parameter.getType());
    }

    @SuppressWarnings("all")
    private Object castToType(String raw, Class<?> type) {
        if (isNumberParam(type)) {
            return NumberUtils.parseNumber(raw, (Class<? extends Number>) type);
        }
        if (type.equals(String.class)) {
            return raw;
        }
        return null;
    }


    private boolean isNumberParam(Class<?> type) {
        return NumberUtils.STANDARD_NUMBER_TYPES.stream()
                .anyMatch(numberType -> TypeUtils.isAssignable(numberType, type));
    }
}
