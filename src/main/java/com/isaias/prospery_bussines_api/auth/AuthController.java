package com.isaias.prospery_bussines_api.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
    private final UtilsService utilsService;
    private final AuthService authService;

    public AuthController(UtilsService utilsService, AuthService authService) {
        this.utilsService = utilsService;
        this.authService = authService;
    }

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

    @PostMapping("/confirmAccount")
    public ResponseEntity<?> confirmationCode(
            @Valid @RequestBody ConfirmationCodeDto confirmationCodeDto
        ){
            return utilsService.handleResponse(
                () -> authService.confirmAccount(confirmationCodeDto)
            );
    }

    @GetMapping("/code")
    @Auth({ RoleConstant.ADMIN, RoleConstant.SELLER, RoleConstant.USER })
    public ResponseEntity<?> secureCode(@GetUser UserEntity user){
        return utilsService.handleResponse(
            () -> authService.getSecureCode(user)
        );
    }
}
