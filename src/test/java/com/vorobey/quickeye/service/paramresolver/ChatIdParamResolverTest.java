package com.vorobey.quickeye.service.paramresolver;

import java.lang.reflect.Method;

import com.vorobey.quickeye.TestUtils;
import com.vorobey.quickeye.annotation.params.ChatId;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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