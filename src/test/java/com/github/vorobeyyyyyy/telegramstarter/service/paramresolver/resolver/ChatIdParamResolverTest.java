package com.github.vorobeyyyyyy.telegramstarter.service.paramresolver.resolver;

import java.lang.reflect.Method;

import com.github.vorobeyyyyyy.telegramstarter.TestUtils;
import com.github.vorobeyyyyyy.telegramstarter.annotation.params.ChatId;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ChatIdParamResolverTest {

    ChatIdParamResolver resolver = new ChatIdParamResolver();

    @Test
    @SneakyThrows
    void resolve() {
        Method method = this.getClass().getDeclaredMethod("handle", Long.class);
        Object resolve = resolver.resolve(
                method,
                method.getParameters()[0],
                TestUtils.getMockedUpdateWithMessage("")
        );
        Assertions.assertEquals(TestUtils.MOCKED_CHAT_ID, resolve);
    }

    //    Utility methods for tests
    void handle(@ChatId Long chatId) {
    }
}