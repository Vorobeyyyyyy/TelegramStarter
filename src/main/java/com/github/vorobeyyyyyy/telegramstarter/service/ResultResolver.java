package com.github.vorobeyyyyyy.telegramstarter.service;

import java.util.Optional;

import com.pengrad.telegrambot.model.Update;

public interface ResultResolver {

    Optional<Runnable> resolve(Update update, Object result);
}
