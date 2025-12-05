package com.isaias.prospery_bussines_api.notification;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.isaias.prospery_bussines_api.notification.interfaces.NotificationChannel;
import com.isaias.prospery_bussines_api.notification.records.Notification;

@Service
public class NotificationService {
    private final Map<String, NotificationChannel> channels;

    public NotificationService(List<NotificationChannel> channels) {
        this.channels = channels.stream().collect(Collectors.toMap(NotificationChannel::getChannel, c -> c));
    }

    public boolean send(Notification record) {
        try {
            if (record == null) throw new IllegalArgumentException("Param record must be provided ");
            NotificationChannel channel = channels.get(record.channel());
            return channel.sendNotification(record.to(), record.message(), record.subject());
        } catch (Exception e) {
            this.handleException(e, "send");
            return false;
        }
    }

    private void handleException(Throwable error, String function){
        System.out.println("[ERROR] -  /notification/NotificationService: " + function);
        System.out.println(error.getMessage());
    }
}
