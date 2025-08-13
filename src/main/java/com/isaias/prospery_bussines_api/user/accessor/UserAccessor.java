package com.isaias.prospery_bussines_api.user.accessor;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.isaias.prospery_bussines_api.common.UtilsService;
import com.isaias.prospery_bussines_api.common.custom_response.ErrorResponse;
import com.isaias.prospery_bussines_api.common.dtos.PaginDto;
import com.isaias.prospery_bussines_api.user.dtos.CreateUserDto;
import com.isaias.prospery_bussines_api.user.dtos.UpdateUserPassDto;
import com.isaias.prospery_bussines_api.user.entity.UserEntity;
import com.isaias.prospery_bussines_api.user.mapper.UserEntityMapper;
import com.isaias.prospery_bussines_api.user.messages_response.UserMessages;
import com.isaias.prospery_bussines_api.user.repository.UserRepository;
import com.isaias.prospery_bussines_api.user.specification.UserSpecification;

@Component
public class UserAccessor {
    private final UserRepository userRepository;
    @Autowired
    private UtilsService utilsService;
    @Autowired
    private UserEntityMapper userEntityMapper;

    public UserAccessor(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser(CreateUserDto createUserDto) {
        try {
            if (this.existsUsername(createUserDto.getUsername()))
                throw ErrorResponse.build(400, UserMessages.USERNAME_ALREADY_EXIST);
            if (this.existsEmail(createUserDto.getEmail()))
                throw ErrorResponse.build(400, UserMessages.EMAIL_ALREADY_EXIST);
            if (this.existsPhoneNumber(createUserDto.getPhone()))
                throw ErrorResponse.build(400, UserMessages.PHONE_NUMBER_ALREADY_EXIST);

            String hashedPassword = utilsService.hashPassword(createUserDto.getPassword());
            String verifyCode = utilsService.generateCode();

            UserEntity user = userEntityMapper.toEntity(createUserDto, hashedPassword);
            user.setVerificationCode(verifyCode);

            userRepository.save(user);
        } catch (Exception e) {
            throw handleException(e, "createUser");
        }
    }

    public UserEntity getUserById(String id) {
        try {
            return userRepository.findById(UUID.fromString(id))
                    .orElseThrow(() -> ErrorResponse.build(404, UserMessages.USER_NOT_FOUND));
        } catch (Exception e) {
            throw handleException(e, "getUserById");
        }
    }

    public Page<UserEntity> getAllUsers(PaginDto paginDto) {
        try {
            Pageable pageable = PageRequest.of(paginDto.getPage(), paginDto.getLimit());
            Specification<UserEntity> spec = UserSpecification.filters(paginDto);
            return userRepository.findAll(spec, pageable);
        } catch (Exception e) {
            throw handleException(e, "getAllUsers");
        }
    }

    public void deleteUserById(String id) {
        try {
            UserEntity user = this.getUserById(id);
            userRepository.delete(user);
        } catch (Exception e) {
            throw handleException(e, "deleteUserById");
        }
    }

    public boolean updateUserPassById(String id, UpdateUserPassDto updateUserDto) {
        try {
            UserEntity user = this.getUserById(id);
            if (!updateUserDto.getCode().equalsIgnoreCase(user.getVerificationCode()))
                return false;

            String hashedPassword = utilsService.hashPassword(updateUserDto.getPassword());
            user.setPassword(hashedPassword);

            String verifyCode = utilsService.generateCode();
            user.setVerificationCode(verifyCode);

            userRepository.save(user);
            return true;
        } catch (Exception e) {
            throw handleException(e, "updateUserById");
        }
    }

    public UserEntity getUserByUsername(String username) {
        try {
            return userRepository.findByUsername(username)
                    .orElseThrow(() -> ErrorResponse.build(404, UserMessages.USER_NOT_FOUND));
        } catch (Exception e) {
            throw handleException(e, "getUserByUsername");
        }
    }

    public boolean existsUsername(String username) {
        try {
            return userRepository.existsByUsername(username);
        } catch (Exception e) {
            throw handleException(e, "exitsUsername");
        }
    }

    public boolean existsEmail(String email) {
        try {
            return userRepository.existsByEmail(email);
        } catch (Exception e) {
            throw handleException(e, "existsEmail");
        }
    }

    public boolean existsPhoneNumber(String phoneNumber) {
        try {
            return userRepository.existsByPhone(phoneNumber);
        } catch (Exception e) {
            throw handleException(e, "existsPhoneNumber");
        }
    }

    private ErrorResponse handleException(Throwable error, String function) {
        System.out.println("[ERROR] -  /user/accessor/UserAccessor: " + function);
        throw utilsService.handleError(error);
    }
}
