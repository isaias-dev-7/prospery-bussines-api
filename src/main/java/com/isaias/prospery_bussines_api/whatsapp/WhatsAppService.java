package com.isaias.prospery_bussines_api.whatsapp;

import org.springframework.stereotype.Service;

import com.isaias.prospery_bussines_api.common.enums.ChannelEnum;
import com.isaias.prospery_bussines_api.notification.interfaces.NotificationChannel;

@Service
public class WhatsAppService implements NotificationChannel {

    @Override
    public String getChannel() {
        return String.valueOf(ChannelEnum.WHATSAPP);
    }

    @Override
    public boolean sendNotification(String to, String message, String subject) {
        throw new UnsupportedOperationException("Unimplemented method 'sendNotification'");
    }
    
}
