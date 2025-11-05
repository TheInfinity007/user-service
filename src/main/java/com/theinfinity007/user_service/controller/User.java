package com.theinfinity007.user_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/users")
public class User {

    @GetMapping("")
    public String getUser() {
        return "hi users";
    }

    @PostMapping("")
    public String signup() {
        return "success";
    }
}
