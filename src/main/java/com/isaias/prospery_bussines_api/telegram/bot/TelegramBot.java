package com.isaias.prospery_bussines_api.telegram.bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public class TelegramBot extends TelegramLongPollingBot {

    @Override
    public void onUpdateReceived(Update arg0) {
        throw new UnsupportedOperationException("Unimplemented method 'onUpdateReceived'");
    }

    @Override
    public String getBotToken() {
        throw new UnsupportedOperationException("Unimplemented method 'getBotToken'");
    }

    @Override
    public String getBotUsername() {
        throw new UnsupportedOperationException("Unimplemented method 'getBotUsername'");
    }
    
}
