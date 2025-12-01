package com.isaias.prospery_bussines_api.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.isaias.prospery_bussines_api.auth.decorators.Auth;
import com.isaias.prospery_bussines_api.auth.decorators.GetUser;
import com.isaias.prospery_bussines_api.auth.dtos.ConfirmationCodeDto;
import com.isaias.prospery_bussines_api.auth.dtos.LoginDto;
import com.isaias.prospery_bussines_api.common.UtilsService;
import com.isaias.prospery_bussines_api.common.roles.RoleConstant;
import com.isaias.prospery_bussines_api.user.dtos.CreateUserDto;
import com.isaias.prospery_bussines_api.user.entity.UserEntity;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired private UtilsService utilsService;
    @Autowired private AuthService authService;

    @PostMapping
    public ResponseEntity<?> login(@Valid @RequestBody LoginDto loginDto){
        return utilsService.handleResponse(
            () -> authService.login(loginDto)
        );
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody CreateUserDto createUserDto){
        return utilsService.handleResponse(
            () -> authService.register(createUserDto)
        );
    }

    @PostMapping("/code")
    public ResponseEntity<?> confirmationCode(
            @Valid @RequestBody ConfirmationCodeDto confirmationCodeDto
        ){
            return utilsService.handleResponse(
                () -> authService.confirmAccount(confirmationCodeDto)
            );
    }
}
