package com.isaias.prospery_bussines_api.config;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;

@Configuration
public class SchedulerConfig {
    @Bean
    public TaskScheduler taskScheduler() {
         ScheduledExecutorService executor = Executors.newScheduledThreadPool(5);
         return new ConcurrentTaskScheduler(executor);
    }
}
