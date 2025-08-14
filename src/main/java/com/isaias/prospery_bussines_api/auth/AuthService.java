package com.isaias.prospery_bussines_api.auth;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.isaias.prospery_bussines_api.auth.dtos.LoginDto;
import com.isaias.prospery_bussines_api.auth.jwt.JwtService;
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
    @Autowired private JwtService jwtService;

    public Response<?> login(LoginDto loginDto) {
        try {
            UserEntity user = userAccessor.getUserByUsername(loginDto.getUsername());
            boolean correctPass = utilsService.verifyPassword(loginDto.getPassword(), user.getPassword());

            if (!correctPass) throw ErrorResponse.build(401, CommonMesajes.INVALID_CREDENTIALS);

            String token = jwtService.generateRefreshToken(user);
            String refreshToken = jwtService.generateRefreshToken(user);

            return SuccessResponse.build(
                    200,
                    Map.ofEntries(
                            Map.entry("username", user.getUsername()),
                            Map.entry("token", token),
                            Map.entry("refreshToken", refreshToken)
                            )
                );
        } catch (Exception e) {
            return handleException(e, "login");
        }
    }

    private ErrorResponse handleException(Throwable error, String function) {
        System.out.println("[ERROR] -  /auth/AuthService: " + function);
        return utilsService.handleError(error);
    }
}
