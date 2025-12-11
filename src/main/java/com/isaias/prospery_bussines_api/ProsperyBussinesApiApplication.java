package com.isaias.prospery_bussines_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
	public static void main(String[] args) {
		SpringApplication.run(ProsperyBussinesApiApplication.class, args);
	}

}
