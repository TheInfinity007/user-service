package com.theinfinity007.user_service.controller;

import com.theinfinity007.user_service.dto.UserCreateRequestDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    @GetMapping("")
    public String getUser() {
        return "hi users";
    }

    @PostMapping("")
    public ResponseEntity<UserCreateRequestDto> signup(@RequestBody @Valid UserCreateRequestDto userCreateRequestDto) {
        System.out.println("userCreateRequestDto = " + userCreateRequestDto);
        return ResponseEntity.ok(userCreateRequestDto);
    }
}
