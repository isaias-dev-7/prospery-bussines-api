package com.isaias.prospery_bussines_api.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.isaias.prospery_bussines_api.auth.decorators.Auth;
import com.isaias.prospery_bussines_api.common.UtilsService;
import com.isaias.prospery_bussines_api.common.dtos.PaginDto;
import com.isaias.prospery_bussines_api.common.roles.RoleConstant;
import com.isaias.prospery_bussines_api.user.dtos.UpdateUserPassDto;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired private UtilsService utilsService;
    @Autowired private UserService userService;

    @GetMapping("/{uuid}")
    @Auth({RoleConstant.ADMIN})
    public ResponseEntity<?> findUser(@PathVariable String uuid){
        return utilsService.handleResponse(
            () -> userService.findUserByUUID(uuid)
        );
    }

    @GetMapping
    @Auth({RoleConstant.ADMIN})
    public ResponseEntity<?> findAll(@Valid PaginDto paginDto){
        return utilsService.handleResponse(
            () -> userService.findAllUser(paginDto)
        );
    }

    @PostMapping("/{uuid}")
    @Auth({RoleConstant.ADMIN, RoleConstant.SELLER, RoleConstant.USER})
    public ResponseEntity<?> updatePassword(
        @PathVariable String uuid,
        @Valid @RequestBody UpdateUserPassDto updateUserPassDto
        ){
        return utilsService.handleResponse(
            () -> userService.updateUserPassById(uuid, updateUserPassDto)
        );
    }

    @DeleteMapping("/{uuid}")
    @Auth({RoleConstant.ADMIN})
    public ResponseEntity<?> delete(@PathVariable String uuid){
        return utilsService.handleResponse(
            () -> userService.deleteUser(uuid)
        );
    }
}
