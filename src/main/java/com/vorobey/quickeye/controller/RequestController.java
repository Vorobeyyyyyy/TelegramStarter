package com.vorobey.quickeye.controller;


import com.pengrad.telegrambot.model.Message;
import com.vorobey.quickeye.annotation.BotController;
import com.vorobey.quickeye.annotation.mappings.CommandMapping;

@BotController
public class RequestController {

    @CommandMapping("/start")
    public void startCommand(Message message) {
        System.out.println(message.chat().id());
    }
}
