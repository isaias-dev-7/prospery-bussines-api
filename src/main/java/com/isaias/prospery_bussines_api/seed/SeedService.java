package com.isaias.prospery_bussines_api.seed;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.isaias.prospery_bussines_api.common.UtilsService;
import com.isaias.prospery_bussines_api.common.custom_response.ErrorResponse;
import com.isaias.prospery_bussines_api.common.custom_response.Response;
import com.isaias.prospery_bussines_api.common.custom_response.SuccessResponse;
import com.isaias.prospery_bussines_api.seed.data.Data;
import com.isaias.prospery_bussines_api.user.entity.UserEntity;
import com.isaias.prospery_bussines_api.user.repository.UserRepository;

@Service
public class SeedService {
    private UserRepository userRepository;
    private UtilsService utilsService;
  
    public SeedService(UserRepository userRepository, UtilsService utilsService) {
        this.userRepository = userRepository;
        this.utilsService = utilsService;
    }

    public Response<?> creatingData() {
        try {
            List<UserEntity> users  = Data.getUserList();
            String hash = utilsService.hashPassword(users.get(0).getPassword());
            for (UserEntity user : users) user.setPassword(hash);
            userRepository.deleteAllInBatch();
            userRepository.saveAll(users);
            return SuccessResponse.build(
                    200,
                    Map.of("message", "SEED EXECUTED SUCCESSFULY")
                );
        } catch (Exception e) {
            return handleException(e, "creatingData");
        }
    }

    private ErrorResponse handleException(Throwable error, String function) {
        System.out.println("[ERROR] -  /user/UserService: " + function);
        return utilsService.handleError(error);
    }
}
