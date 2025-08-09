package com.isaias.prospery_bussines_api.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @GetMapping("/saludo")
    public String Saludo(){
        return "hello world";
    }
}
