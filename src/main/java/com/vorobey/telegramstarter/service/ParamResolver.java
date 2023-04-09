package com.vorobey.telegramstarter.service;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import com.pengrad.telegrambot.model.Update;

public interface ParamResolver {

    Object resolve(Method method, Parameter parameter, Update update);
}
