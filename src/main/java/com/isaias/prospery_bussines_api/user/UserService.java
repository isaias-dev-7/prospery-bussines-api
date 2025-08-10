package com.isaias.prospery_bussines_api.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.isaias.prospery_bussines_api.common.UtilsService;
import com.isaias.prospery_bussines_api.common.custom_response.ErrorResponse;
import com.isaias.prospery_bussines_api.common.custom_response.Response;
import com.isaias.prospery_bussines_api.common.messages_response.CommonMesajes;

@Service
public class UserService {
    @Autowired
    private UtilsService utilsService;
    
    public Response<?> getSome(){
        try {
            throw ErrorResponse.build(400, CommonMesajes.INVALID_PARAM);
        } catch (Exception error) {
            return this.handleException(error);
        }
    }

    private ErrorResponse handleException(Throwable error){
        return utilsService.handleError(error);
    }
}