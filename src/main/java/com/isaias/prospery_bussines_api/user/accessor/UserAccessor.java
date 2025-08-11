package com.isaias.prospery_bussines_api.user.accessor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.isaias.prospery_bussines_api.common.UtilsService;
import com.isaias.prospery_bussines_api.common.custom_response.ErrorResponse;
import com.isaias.prospery_bussines_api.user.dtos.CreateUserDto;
import com.isaias.prospery_bussines_api.user.mapper.UserEntityMapper;
import com.isaias.prospery_bussines_api.user.messages_response.UserMessages;
import com.isaias.prospery_bussines_api.user.repository.UserRepository;

@Component
public class UserAccessor {
    private final UserRepository userRepository;
    @Autowired private UtilsService utilsService;
    @Autowired private UserEntityMapper userEntityMapper;

    public UserAccessor(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser(CreateUserDto createUserDto) {
        try {
            if(this.existsUsername(createUserDto.username)) 
                throw ErrorResponse.build(400,UserMessages.USERNAME_ALREADY_EXIST);
            if(this.existsEmail(createUserDto.email))
                throw ErrorResponse.build(400,UserMessages.EMAIL_ALREADY_EXIST);
            if(this.existsPhoneNumber(createUserDto.phone))
                throw ErrorResponse.build(400,UserMessages.PHONE_NUMBER_ALREADY_EXIST);

            String hashedPassword = utilsService.hashPassword(createUserDto.password);
            userRepository.save(userEntityMapper.toEntity(createUserDto, hashedPassword));
        } catch (Exception e) {
            throw handleException(e, "createUser");
        }
    }

    public boolean existsUsername(String username){
        try {
            return userRepository.existsByUsername(username);
        } catch (Exception e) {
            throw handleException(e, "exitsUsername");
        }
    }

    public boolean existsEmail(String email){
        try {
            return userRepository.existsByEmail(email);
        } catch (Exception e) {
            throw handleException(e, "existsEmail");
        }
    }

    public boolean existsPhoneNumber(String phoneNumber){
        try {
            return userRepository.existsByPhone(phoneNumber);
        } catch (Exception e) {
             throw handleException(e, "existsPhoneNumber");
        }
    }

    private ErrorResponse handleException(Throwable error, String function){
        System.out.println("[ERROR] -  /user/accessor/UserAccessor: " + function);
        throw utilsService.handleError(error);
    }
}
