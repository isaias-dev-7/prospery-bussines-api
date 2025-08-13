package com.isaias.prospery_bussines_api.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.isaias.prospery_bussines_api.auth.dtos.LoginDto;
import com.isaias.prospery_bussines_api.common.UtilsService;

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
}
