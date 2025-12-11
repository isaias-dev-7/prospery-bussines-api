package com.isaias.prospery_bussines_api.seed.data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.isaias.prospery_bussines_api.common.enums.RoleEnum;
import com.isaias.prospery_bussines_api.user.entity.UserEntity;

public final class Data {
    public static List<UserEntity> getUserList() {
        List <UserEntity> users = new ArrayList<>();
        int count = 300;
        String [] usernames = randomsUserNames(count);
        String [] emails = randomsEmails(count, usernames);
        String [] phoneNumbers = randomsPhoneNumbers(count);
        LocalDateTime now = LocalDateTime.now();
        
        for (int i = 0; i < count; i++) {
            UserEntity user = new UserEntity();
            user.setUsername(usernames[i]);
            user.setEmail(emails[i]);
            user.setPhone(phoneNumbers[i]);
            user.setPassword("Marshall123");
            user.setRole(RoleEnum.USER);
            user.setVerificationCode("Msh789");
            user.setActive(true);
            user.setCreatedAt(now);
            users.add(user);
        }
        
        return users;
    }

    private static String [] randomsUserNames(int count) {
        Random random = new Random();
        Set<String> usersNames = new HashSet<>();
        String letters = "abcdefghijklmnopqrstuvwxyz";

        while(usersNames.size() < count) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < 8; j++) sb.append(letters.charAt(random.nextInt(letters.length())));
            for (int j = 0; j < 3; j++) sb.append(random.nextInt(10));
            usersNames.add(sb.toString());
        }
        
        return usersNames.toArray(new String[0]);
    }

    private static String [] randomsEmails(int count, String [] usernames) {
        List <String> emails = new ArrayList<>();
        for(String name : usernames) emails.add(name + "@gmail.com");
        
        return emails.toArray(new String[0]);
    } 

    private static String [] randomsPhoneNumbers(int count) {
       Random random = new Random();
       Set<String> phoneNumbers = new HashSet<>();
       String numbers = "1234567890";
       while(phoneNumbers.size() < count) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < 8; j++) sb.append(numbers.charAt(random.nextInt(numbers.length())));
            phoneNumbers.add("+53"+sb.toString());
        }
        
       return phoneNumbers.toArray(new String [0]);
   }
}
