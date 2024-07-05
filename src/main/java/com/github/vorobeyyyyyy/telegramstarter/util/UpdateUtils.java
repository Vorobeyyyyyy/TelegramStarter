package com.github.vorobeyyyyyy.telegramstarter.util;

import java.util.Optional;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import org.springframework.lang.Nullable;

public class UpdateUtils {
    @Nullable
    public static String getText(Update update) {
        return Optional.ofNullable(update).map(Update::message).map(Message::text).orElse(null);
    }

    public static Optional<Long> getChatId(Update update) {
        return Optional.ofNullable(update)
                .map(Update::message)
                .map(Message::chat)
                .map(Chat::id);
    }

    private UpdateUtils() {
    }
}
