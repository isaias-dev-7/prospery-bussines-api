package com.isaias.prospery_bussines_api.user.accessor;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.isaias.prospery_bussines_api.auth.dtos.ConfirmationCodeDto;
import com.isaias.prospery_bussines_api.common.UtilsService;
import com.isaias.prospery_bussines_api.common.custom_response.ErrorResponse;
import com.isaias.prospery_bussines_api.common.dtos.PaginDto;
import com.isaias.prospery_bussines_api.common.enums.ChannelEnum;
import com.isaias.prospery_bussines_api.common.messages_response.CommonMesajes;
import com.isaias.prospery_bussines_api.notification.NotificationService;
import com.isaias.prospery_bussines_api.notification.records.Notification;
import com.isaias.prospery_bussines_api.scheduled_tasks.ScheduledTaskService;
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
    private final UtilsService utilsService;
    private final NotificationService notificationService;
    @Autowired @Lazy private ScheduledTaskService scheduledTaskService;

    public UserAccessor(
        UserRepository userRepository, 
        UtilsService utilsService,
        UserEntityMapper userEntityMapper, 
        NotificationService notificationService
        ) {
        this.userRepository = userRepository;
        this.notificationService = notificationService;
        this.utilsService = utilsService;
    }

    public UserEntity createUser(CreateUserDto createUserDto) {
        try {
            if (this.existsUsername(createUserDto.getUsername()))
                throw ErrorResponse.build(400, UserMessages.USERNAME_ALREADY_EXIST);
            if (this.existsEmail(createUserDto.getEmail()))
                throw ErrorResponse.build(400, UserMessages.EMAIL_ALREADY_EXIST);
            if (this.existsPhoneNumber(createUserDto.getPhone()))
                throw ErrorResponse.build(400, UserMessages.PHONE_NUMBER_ALREADY_EXIST);

            String hashedPassword = utilsService.hashPassword(createUserDto.getPassword());
            String verifyCode = utilsService.generateCode();

            UserEntity user = UserEntityMapper.toEntity(createUserDto, hashedPassword);
            LocalDateTime now = LocalDateTime.now();
            user.setVerificationCode(verifyCode);
            user.setCreatedAt(now);
            boolean notificationSent = notificationService.send(
                new Notification(
                    ChannelEnum.EMAIL.toString(),
                    user.getEmail(), 
                    verifyCode, 
                    "Código de verificación"
                )
            );

            if(!notificationSent) throw ErrorResponse.build(400, CommonMesajes.FAIL_EMAIL);
            scheduledTaskService.deleteUserAfterFiveMinutesInactive(user.getUsername());
            return userRepository.save(user);
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

            this.saveWithNewSecureCode(user);
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

    public UserEntity getUserByEmail(String email){
        try {
            return userRepository.findByEmail(email)
                .orElseThrow(() -> ErrorResponse.build(404, UserMessages.USER_NOT_FOUND));
        } catch (Exception e) {
            throw handleException(e, "getUserByEmail");
        }
    }

    public void saveUser(UserEntity user){
        try {
            userRepository.save(user);
        } catch (Exception e) {
            throw handleException(e, "saveUser");
        }
    }

    public void sendVerificationCodeToUser(String email){
        try {
            UserEntity user = this.getUserByEmail(email);
            boolean notificationSent = notificationService.send(
                new Notification(
                    ChannelEnum.EMAIL.toString(),
                    user.getEmail(), 
                    user.getVerificationCode(), 
                    "Código de verificación"
                )
            );

           if(!notificationSent) throw ErrorResponse.build(400, CommonMesajes.FAIL_EMAIL);
        } catch (Exception e) {
            throw handleException(e, "sendCodeVeryfication");
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

    public boolean confirmAccount(ConfirmationCodeDto confirmationCodeDto) {
        try {
            UserEntity user = this.getUserByUsername(confirmationCodeDto.getUsername());
            boolean confirm = user.getVerificationCode().equals(confirmationCodeDto.getCode());

            if(confirm){
                user.setActive(true);
                this.saveWithNewSecureCode(user);
            }

            return confirm;
        } catch (Exception e) {
            throw handleException(e, "confirmAccount");
        }
    }

    public void saveWithNewSecureCode(UserEntity user){
        try {
            String verifyCode = utilsService.generateCode();
            user.setVerificationCode(verifyCode);
            this.saveUser(user);
        } catch (Exception e) {
            throw handleException(e, "newSecureCode");
        }
    }

    private ErrorResponse handleException(Throwable error, String function) {
        System.out.println("[ERROR] -  /user/accessor/UserAccessor: " + function);
        throw utilsService.handleError(error);
    }
}
