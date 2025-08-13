package com.isaias.prospery_bussines_api.auth;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.isaias.prospery_bussines_api.auth.dtos.LoginDto;
import com.isaias.prospery_bussines_api.common.UtilsService;
import com.isaias.prospery_bussines_api.common.custom_response.ErrorResponse;
import com.isaias.prospery_bussines_api.common.custom_response.Response;
import com.isaias.prospery_bussines_api.common.custom_response.SuccessResponse;
import com.isaias.prospery_bussines_api.common.messages_response.CommonMesajes;
import com.isaias.prospery_bussines_api.user.accessor.UserAccessor;
import com.isaias.prospery_bussines_api.user.entity.UserEntity;

@Service
public class AuthService {
    @Autowired private UtilsService utilsService;
    @Autowired private UserAccessor userAccessor;

    public Response<?> login(LoginDto loginDto){
        try {
            UserEntity user = userAccessor.getUserByUsername(loginDto.getUsername());
            boolean correctPass = utilsService.verifyPassword(loginDto.getPassword(), user.getPassword());
            
            return correctPass ? 
            SuccessResponse.build(200, Map.of("message", "perfect")) 
            : 
            ErrorResponse.build(401, CommonMesajes.INVALID_CREDENTIALS);
        } catch (Exception e) {
            return handleException(e, "login");
        }
    }

     private ErrorResponse handleException(Throwable error, String function){
        System.out.println("[ERROR] -  /auth/AuthService: " + function);
        return utilsService.handleError(error);
    }
}
