package com.isaias.prospery_bussines_api.scheduled_tasks;

import java.time.Duration;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import com.isaias.prospery_bussines_api.common.UtilsService;
import com.isaias.prospery_bussines_api.user.accessor.UserAccessor;
import com.isaias.prospery_bussines_api.user.entity.UserEntity;

@Service
public class ScheduledTaskService {
     @Autowired private TaskScheduler scheduler;
     @Autowired private UserAccessor userAccessor;
     @Autowired private UtilsService utilsService;

    public void deleteUserAfterFiveMinutesInactive(String username){
        try {
            executeWithDelay(() -> {
                UserEntity user = userAccessor.getUserByUsername(username);
                if(!user.isActive()){
                    userAccessor.deleteUserById(String.valueOf(user.getId()));
                }else {
                    String verifyCode = utilsService.generateCode();
                    user.setVerificationCode(verifyCode);
                    userAccessor.saveUser(user);
                }

            }, Duration.ofMinutes(1));
        } catch (Exception e) {
            handleException(e, "deleteUserAfterFiveMinutesInactive");
        }
    }

    private void executeWithDelay(Runnable task, Duration delay) {
        scheduler.schedule(task, Instant.now().plus(delay));
    }

    private void handleException(Throwable error, String function){
        System.out.println("[ERROR] -  /schedule_tasks/ScheduledTaskService: " + function);
        System.out.println(error.getMessage());
    }
}
