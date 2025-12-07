package com.isaias.prospery_bussines_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import com.isaias.prospery_bussines_api.telegram.bot.TelegramBot;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class ProsperyBussinesApiApplication {
	static {
            Dotenv dotenv = Dotenv.configure()
                .directory("./")
                .ignoreIfMissing()
                .load();

            dotenv.entries().forEach(e -> System.setProperty(e.getKey(), e.getValue()));
    }
	public static void main(String[] args) throws TelegramApiException {
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(new TelegramBot());
		SpringApplication.run(ProsperyBussinesApiApplication.class, args);
	}

}
