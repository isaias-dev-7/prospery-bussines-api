package com.isaias.prospery_bussines_api.user;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.isaias.prospery_bussines_api.common.UtilsService;
import com.isaias.prospery_bussines_api.common.custom_response.ErrorResponse;
import com.isaias.prospery_bussines_api.common.custom_response.Response;
import com.isaias.prospery_bussines_api.common.custom_response.SuccessResponse;
import com.isaias.prospery_bussines_api.user.accessor.UserAccessor;
import com.isaias.prospery_bussines_api.user.dtos.CreateUserDto;
import com.isaias.prospery_bussines_api.user.messages_response.UserMessages;

@Service
public class UserService {
    @Autowired private UtilsService utilsService;
    @Autowired private UserAccessor userAccessor;

    public Response<?> createUser(CreateUserDto createUserDto){
        try {
            userAccessor.createUser(createUserDto);
            return SuccessResponse.build(200, Map.of("message", UserMessages.USER_CREATED));
        } catch (Exception e) {
            return handleException(e, "createUser");
        }
    }

    private ErrorResponse handleException(Throwable error, String function){
        System.out.println("[ERROR] -  /user/UserService: " + function);
        return utilsService.handleError(error);
    }
}