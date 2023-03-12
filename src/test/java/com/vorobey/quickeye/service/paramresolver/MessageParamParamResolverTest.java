package com.vorobey.quickeye.service.paramresolver;

import java.lang.reflect.Method;
import java.math.BigDecimal;

import com.vorobey.quickeye.TestUtils;
import com.vorobey.quickeye.annotation.mappings.CommandMapping;
import com.vorobey.quickeye.annotation.params.MessageParam;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.util.AntPathMatcher;

@SuppressWarnings("all")
class MessageParamParamResolverTest {

    MessageParamParamResolver resolver = new MessageParamParamResolver(new AntPathMatcher());

    @Test
    void resolveInteger() {
        testType(22);
    }

    @Test
    void resolveDouble() {
        testType(22.2D);
    }

    @Test
    void resolveBigDecimal() {
        testType(new BigDecimal("22.2"));
    }

    @Test
    void resolveString() {
        testType("testString");
    }

    @SneakyThrows
    <T> void testType(T value) {
        Method method = this.getClass().getDeclaredMethod("handler", value.getClass());
        var update = TestUtils.getMockedUpdateWithMessage("/test " + value);
        Object resolve = resolver.resolve(method, method.getParameters()[0], update);
        Assertions.assertEquals(resolve, value);
    }

    //    Utility methods for tests
    @CommandMapping("/test {value}")
    void handler(@MessageParam Integer value) {
    }
    @CommandMapping("/test {value}")
    void handler(@MessageParam Double value) {
    }
    @CommandMapping("/test {value}")
    void handler(@MessageParam BigDecimal value) {
    }
    @CommandMapping("/test {value}")
    void handler(@MessageParam String value) {
    }

}