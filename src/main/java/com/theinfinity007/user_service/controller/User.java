package com.theinfinity007.user_service.controller;

import com.theinfinity007.user_service.dto.UserCreateRequestDto;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/users")
public class User {

    @GetMapping("")
    public String getUser() {
        return "hi users";
    }

    @PostMapping("")
    public String signup(@RequestBody UserCreateRequestDto userCreateRequestDto) {
        System.out.println("userCreateRequestDto = " + userCreateRequestDto);
        return "success";
    }
}
