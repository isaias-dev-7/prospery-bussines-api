package com.isaias.prospery_bussines_api.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.isaias.prospery_bussines_api.common.UtilsService;
import com.isaias.prospery_bussines_api.common.dtos.PaginDto;
import com.isaias.prospery_bussines_api.user.dtos.CreateUserDto;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired private UtilsService utilsService;
    @Autowired private UserService userService;

    @GetMapping("/{uuid}")
    public ResponseEntity<?> findUser(@PathVariable String uuid){
        return utilsService.handleResponse(
            () -> userService.findUserByUUID(uuid)
        );
    }

    @GetMapping
    public ResponseEntity<?> findAll(@Valid PaginDto paginDto){
        return utilsService.handleResponse(
            () -> userService.findAllUser(paginDto)
        );
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CreateUserDto createUserDto){
        return utilsService.handleResponse(
            () -> userService.createUser(createUserDto)
        );
    }
}
