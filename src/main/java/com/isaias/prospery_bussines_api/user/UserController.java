package com.isaias.prospery_bussines_api.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.isaias.prospery_bussines_api.common.UtilsService;

@RestController
public class UserController {
    @Autowired
    private UtilsService utilsService;

    @Autowired
    private UserService userService;

    @GetMapping("/saludo")
    public ResponseEntity<?> Saludo(){
        return utilsService.handleResponse(() -> userService.getSome());
    }
}
