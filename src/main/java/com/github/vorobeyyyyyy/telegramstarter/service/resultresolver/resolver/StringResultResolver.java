package com.github.vorobeyyyyyy.telegramstarter.service.resultresolver.resolver;


import java.util.Optional;

import com.github.vorobeyyyyyy.telegramstarter.service.ResultResolver;
import com.github.vorobeyyyyyy.telegramstarter.util.UpdateUtils;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StringResultResolver implements ResultResolver {

    private final TelegramBot telegramBot;

    @Override
    public Optional<Runnable> resolve(Update update, Object result) {
        return UpdateUtils.getChatId(update)
                .filter(i -> result instanceof String)
                .map(chatId -> () -> telegramBot.execute(new SendMessage(chatId, (String) result)));

    }
}
