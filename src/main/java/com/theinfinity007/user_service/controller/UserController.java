package com.theinfinity007.user_service.controller;

import com.theinfinity007.user_service.dto.UserCreateRequestDto;
import com.theinfinity007.user_service.entity.User;
import com.theinfinity007.user_service.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("")
    public String getUser() {
        return "hi users";
    }

    @PostMapping("")
    public ResponseEntity<User> signup(@RequestBody @Valid UserCreateRequestDto userCreateRequestDto) {
        System.out.println("userCreateRequestDto = " + userCreateRequestDto);

        User user = new User();
        user.setEmail(userCreateRequestDto.getEmail());
        user.setPassword(userCreateRequestDto.getPassword());
        user.setFirstName(userCreateRequestDto.getFirstName());
        user.setLastName(userCreateRequestDto.getLastName());
        user.setBirthday(userCreateRequestDto.getBirthday());
        user.setGender(userCreateRequestDto.getGender());
        user.setTitle(userCreateRequestDto.getTitle());

        userService.create(user);

        return ResponseEntity.ok(user);
    }
}
