package com.vorobey.quickeye;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import org.mockito.Mockito;

public class TestUtils {

    public static final long MOCKED_CHAT_ID = 75723L;

    public static Update getMockedUpdateWithMessage(String messageText) {
        Update mockedUpdate = Mockito.mock(Update.class);
        Message mockedMessage = Mockito.mock(Message.class);
        Chat mockedChat = Mockito.mock(Chat.class);

        Mockito.when(mockedMessage.text()).thenReturn(messageText);
        Mockito.when(mockedMessage.chat()).thenReturn(mockedChat);

        Mockito.when(mockedUpdate.message()).thenReturn(mockedMessage);

        Mockito.when(mockedChat.id()).thenReturn(MOCKED_CHAT_ID);

        return mockedUpdate;
    }
}
