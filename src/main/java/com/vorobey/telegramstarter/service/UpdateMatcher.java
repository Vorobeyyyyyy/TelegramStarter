package com.vorobey.telegramstarter.service;

import java.lang.annotation.Annotation;

import com.pengrad.telegrambot.model.Update;

public interface UpdateMatcher<T extends Annotation> {
    int NOT_MATCH_PRIORITY = 0;
    int DEFAULT_PRIORITY = 100;

    /**
     * @param update     update to check
     * @param annotation annotation instance
     * @return priority of mapping, 0 equals
     */
    int matches(Update update, T annotation);

    Class<T> getAnnotationClass();
}
