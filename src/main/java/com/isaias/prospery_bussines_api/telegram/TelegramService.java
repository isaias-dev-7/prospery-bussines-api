package com.isaias.prospery_bussines_api.telegram;

import org.springframework.stereotype.Service;

import com.isaias.prospery_bussines_api.common.enums.ChannelEnum;
import com.isaias.prospery_bussines_api.notification.interfaces.NotificationChannel;

@Service
public class TelegramService implements NotificationChannel {
    @Override
    public String getChannel() {
        return String.valueOf(ChannelEnum.TELEGRAM);       
    }

    @Override
    public boolean sendNotification(String to, String message, String subject) {
       return true; //TODO 
    }
    
}
