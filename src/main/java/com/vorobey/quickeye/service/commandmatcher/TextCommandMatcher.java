package com.vorobey.quickeye.service.commandmatcher;

import com.pengrad.telegrambot.model.Update;
import com.vorobey.quickeye.annotation.mappings.CommandMapping;
import com.vorobey.quickeye.service.UpdateMatcher;
import com.vorobey.quickeye.util.UpdateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.PathMatcher;

import static java.util.Objects.isNull;

@Component
@RequiredArgsConstructor
public class TextCommandMatcher implements UpdateMatcher<CommandMapping> {

    private final PathMatcher pathMatcher;

    @Override
    public int matches(Update update, CommandMapping annotation) {
        int priority = UpdateMatcher.NOT_MATCH_PRIORITY;

        String text = UpdateUtils.getText(update);
        if (isNull(text) || isNull(annotation))  {
            return priority;
        }

        if (pathMatcher.match(annotation.value(), text)) {
            priority += UpdateMatcher.DEFAULT_PRIORITY;
        }

        return priority;
    }

    @Override
    public Class<CommandMapping> getAnnotationClass() {
        return CommandMapping.class;
    }
}
