package com.isaias.prospery_bussines_api.telegram.bot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class TelegramBot extends TelegramLongPollingBot {
    @Value("${spring.telegram.username}")
    private String username;

    @Value("${spring.telegram.bot-token}")
    private String botToken;

    @Override
    public void onUpdateReceived(Update arg0) {
        System.out.println(arg0.getMessage().getText());
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public String getBotUsername() {
        return username;
    }
    
}
