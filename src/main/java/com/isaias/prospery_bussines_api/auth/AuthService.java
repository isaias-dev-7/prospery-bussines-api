package com.isaias.prospery_bussines_api.auth;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.isaias.prospery_bussines_api.auth.dtos.ConfirmationCodeDto;
import com.isaias.prospery_bussines_api.auth.dtos.LoginDto;
import com.isaias.prospery_bussines_api.auth.jwt.JwtService;
import com.isaias.prospery_bussines_api.common.UtilsService;
import com.isaias.prospery_bussines_api.common.custom_response.ErrorResponse;
import com.isaias.prospery_bussines_api.common.custom_response.Response;
import com.isaias.prospery_bussines_api.common.custom_response.SuccessResponse;
import com.isaias.prospery_bussines_api.common.messages_response.CommonMesajes;
import com.isaias.prospery_bussines_api.user.accessor.UserAccessor;
import com.isaias.prospery_bussines_api.user.dtos.CreateUserDto;
import com.isaias.prospery_bussines_api.user.entity.UserEntity;
import com.isaias.prospery_bussines_api.user.messages_response.UserMessages;

@Service
public class AuthService {
    private final UtilsService utilsService;
    private final UserAccessor userAccessor;
    private final JwtService jwtService;

    public AuthService(UtilsService utilsService, UserAccessor userAccessor, JwtService jwtService) {
        this.utilsService = utilsService;
        this.userAccessor = userAccessor;
        this.jwtService = jwtService;
    }

    public Response<?> login(LoginDto loginDto) {
        try {
            UserEntity user = userAccessor.getUserByUsername(loginDto.getUsername());
            boolean correctPass = utilsService.verifyPassword(loginDto.getPassword(), user.getPassword());

            if(!correctPass) throw ErrorResponse.build(401, CommonMesajes.INVALID_CREDENTIALS);
            if(!user.isActive()) throw ErrorResponse.build(401, "User not active");
            String token = jwtService.generateToken(user);

            return SuccessResponse.build(
                    200,
                    Map.ofEntries(
                            Map.entry("username", user.getUsername()),
                            Map.entry("token", token)
                            )
                );
        } catch (Exception e) {
            return handleException(e, "login");
        }
    }

    public Response<?> register(CreateUserDto createUserDto){
        try {
            UserEntity user = userAccessor.createUser(createUserDto);
            return SuccessResponse.build(
                200, 
                Map.ofEntries(
                            Map.entry("message", user.getUsername() + " " + CommonMesajes.ACTIVATION_CODE),
                            Map.entry("username", user.getUsername())
                            )
            );
        } catch (Exception e) {
            return handleException(e, "register");
        }
    }

    public Response<?> confirmAccount(ConfirmationCodeDto confirmationCodeDto){
        try {
            boolean confirm = userAccessor.confirmAccount(confirmationCodeDto);
            String message =  confirm ? UserMessages.USER_ACCOUNT_ACTIVATED : UserMessages.USER_ACCOUNT_FAIL_CONFIRMATION;
             
            return SuccessResponse.build(
                confirm ? 200 : 401, 
                Map.of("message", message)
            );
        } catch (Exception e) {
            return handleException(e, "confirmAccount");
        }
    }

    public Response<?> getSecureCode(UserEntity user){
        try {
            return SuccessResponse.build(
                200, 
                Map.of("code", user.getVerificationCode())
            );
        } catch (Exception e) {
            return handleException(e, "getSecureCode");
        }
    }


    private ErrorResponse handleException(Throwable error, String function) {
        System.out.println("[ERROR] -  /auth/AuthService: " + function);
        return utilsService.handleError(error);
    }
}
