package com.isaias.prospery_bussines_api.user.mapper;

import org.springframework.stereotype.Component;

import com.isaias.prospery_bussines_api.common.enums.Role;
import com.isaias.prospery_bussines_api.user.dtos.CreateUserDto;
import com.isaias.prospery_bussines_api.user.entity.UserEntity;

@Component
public class UserEntityMapper {

    public UserEntity toEntity(CreateUserDto createUserDto, String hashedPassword){
        UserEntity user = new UserEntity();
        user.setPassword(hashedPassword);
        user.setEmail(createUserDto.getEmail());
        user.setPhone(createUserDto.getPhone());
        user.setUsername(createUserDto.getUsername());
        user.setRole(Role.valueOf(createUserDto.getRole()));
        return user;
    }
}
