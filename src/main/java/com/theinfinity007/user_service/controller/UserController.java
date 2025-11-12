package com.theinfinity007.user_service.controller;

import com.theinfinity007.user_service.dto.UserCreateRequestDto;
import com.theinfinity007.user_service.dto.UserResponseDto;
import com.theinfinity007.user_service.entity.User;
import com.theinfinity007.user_service.mapper.request.UserRequestMapper;
import com.theinfinity007.user_service.mapper.response.UserResponseMapper;
import com.theinfinity007.user_service.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRequestMapper userRequestMapper;

    @GetMapping("")
    public String getUser() {
        return "hi users";
    }

    @PostMapping("")
    public ResponseEntity<UserResponseDto> signup(@RequestBody @Valid UserCreateRequestDto userCreateRequestDto) {

        User user = userRequestMapper.populate(userCreateRequestDto);

        User createdUser = userService.create(user);

        UserResponseDto response = UserResponseMapper.populate(createdUser);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
