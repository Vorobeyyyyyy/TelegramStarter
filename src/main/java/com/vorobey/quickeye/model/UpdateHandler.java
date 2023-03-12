package com.vorobey.quickeye.model;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public record UpdateHandler(
        Method method, Object controller, Annotation annotation
) {
    public Object invoke(Object... args) {
        try {
            return method.invoke(controller, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
