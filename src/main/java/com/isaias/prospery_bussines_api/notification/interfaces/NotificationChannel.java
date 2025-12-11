package com.isaias.prospery_bussines_api.notification.interfaces;

public interface NotificationChannel {
    String getChannel();
    boolean sendNotification(String to, String message, String subject);
}
