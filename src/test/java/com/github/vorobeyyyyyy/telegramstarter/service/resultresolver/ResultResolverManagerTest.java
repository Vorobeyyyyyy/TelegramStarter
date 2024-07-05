package com.github.vorobeyyyyyy.telegramstarter.service.resultresolver;

import com.github.vorobeyyyyyy.telegramstarter.TestConfig;
import com.github.vorobeyyyyyy.telegramstarter.TestUtils;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.verify;

@SpringBootTest(classes = TestConfig.class)
class ResultResolverManagerTest {

    @Autowired
    ResultResolverManager resultResolverManager;
    @Autowired
    TelegramBot telegramBot;

    @Test
    void testString() {
        resultResolverManager.resolveResponse(
                TestUtils.getMockedUpdateWithMessage(""),
                "Test response"
        );
        verify(telegramBot).execute(Mockito.any(SendMessage.class));
    }
}