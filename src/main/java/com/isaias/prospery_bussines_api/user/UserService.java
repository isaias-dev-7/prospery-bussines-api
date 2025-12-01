package com.isaias.prospery_bussines_api.user;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.isaias.prospery_bussines_api.common.UtilsService;
import com.isaias.prospery_bussines_api.common.custom_response.ErrorResponse;
import com.isaias.prospery_bussines_api.common.custom_response.PaginResponse;
import com.isaias.prospery_bussines_api.common.custom_response.Response;
import com.isaias.prospery_bussines_api.common.custom_response.SuccessResponse;
import com.isaias.prospery_bussines_api.common.dtos.PaginDto;
import com.isaias.prospery_bussines_api.common.messages_response.CommonMesajes;
import com.isaias.prospery_bussines_api.user.accessor.UserAccessor;
import com.isaias.prospery_bussines_api.user.dtos.UpdateUserPassDto;
import com.isaias.prospery_bussines_api.user.entity.UserEntity;
import com.isaias.prospery_bussines_api.user.messages_response.UserMessages;

@Service
public class UserService {
    private final UtilsService utilsService;
    private final UserAccessor userAccessor;

    public UserService(UtilsService utilsService, UserAccessor userAccessor) {
        this.utilsService = utilsService;
        this.userAccessor = userAccessor;
    }

    public Response<?> findUserByUUID(String uuid) {
        try {
            UserEntity user = userAccessor.getUserById(uuid);
            return SuccessResponse.build(200, Map.of("user", user));
        } catch (Exception e) {
            return handleException(e, "findUserByUUID");
        }
    }

    public Response<?> findAllUser(PaginDto paginDto) {
        try {
            Page<UserEntity> page = userAccessor.getAllUsers(paginDto);
            return PaginResponse.build(
                    200,
                    page.getContent(),
                    page.getTotalElements(),
                    page.getNumber(),
                    paginDto.getLimit(),
                    page.getTotalPages());
        } catch (Exception e) {
            return handleException(e, "findAllUser");
        }
    }

    public Response<?> updateUserPassById(String uuid, UpdateUserPassDto updateUserDto) {
        try {
            return userAccessor.updateUserPassById(uuid, updateUserDto)
                    ? SuccessResponse.build(200, Map.of("message", UserMessages.USER_PASS_UPDATED))
                    : ErrorResponse.build(400, UserMessages.USER_PASS_NOT_UPDATED);
        } catch (Exception e) {
            return handleException(e, "updateUserPassById");
        }
    }

    public Response<?> deleteUser(String uuid) {
        try {
            userAccessor.deleteUserById(uuid);
            return SuccessResponse.build(204, "");
        } catch (Exception e) {
            return handleException(e, "deleteUser");
        }
    }

    public Response<?> getVerificationCode(String email) {
        try {
            userAccessor.sendVerificationCodeToUser(email);
            return SuccessResponse.build(
                    200,
                    Map.of("message", CommonMesajes.ACTIVATION_CODE));
        } catch (Exception e) {
            return handleException(e, "getVerificationCode");
        }
    }

    private ErrorResponse handleException(Throwable error, String function) {
        System.out.println("[ERROR] -  /user/UserService: " + function);
        return utilsService.handleError(error);
    }
}