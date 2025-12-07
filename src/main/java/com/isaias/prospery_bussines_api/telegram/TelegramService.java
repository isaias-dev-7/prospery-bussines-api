package com.isaias.prospery_bussines_api.telegram;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.isaias.prospery_bussines_api.common.enums.ChannelEnum;
import com.isaias.prospery_bussines_api.notification.interfaces.NotificationChannel;

@Service
public class TelegramService implements NotificationChannel {
    @Value("${spring.telegram.bot-token}")
    private String botToken;

    private final WebClient webClient;

    public TelegramService() {
        this.webClient = WebClient.builder()
                .baseUrl("https://api.telegram.org/bot" + botToken)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Override
    public String getChannel() {
        return String.valueOf(ChannelEnum.TELEGRAM);       
    }

    @Override
    public boolean sendNotification(String to, String message, String subject) {
       String res = webClient.post().uri("/sendMessage")
                .bodyValue(Map.of("chat_id", to, "text", message))
                .retrieve()
                .bodyToMono(String.class)
                .block();

       return res != null && res.contains("\"ok\":true");
    }
    
}
