package com.theinfinity007.user_service.controller;

import com.theinfinity007.user_service.dto.JWTResponse;
import com.theinfinity007.user_service.dto.UserCreateRequestDto;
import com.theinfinity007.user_service.dto.UserResponseDto;
import com.theinfinity007.user_service.dto.UserLoginRequestDto;
import com.theinfinity007.user_service.entity.User;
import com.theinfinity007.user_service.mapper.request.UserRequestMapper;
import com.theinfinity007.user_service.mapper.response.UserResponseMapper;
import com.theinfinity007.user_service.service.LoginService;
import com.theinfinity007.user_service.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final LoginService loginService;
    private final UserRequestMapper userRequestMapper;

    @GetMapping("/{email}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable String email) {
        User user = userService.findByEmail(email);

        UserResponseDto response = UserResponseMapper.populate(user);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("")
    public ResponseEntity<UserResponseDto> signup(@RequestBody @Valid UserCreateRequestDto userCreateRequestDto) {

        User user = userRequestMapper.populate(userCreateRequestDto);

        User createdUser = userService.create(user);

        UserResponseDto response = UserResponseMapper.populate(createdUser);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public JWTResponse login(@RequestBody @Valid UserLoginRequestDto userLoginRequestDto){

        String email = userLoginRequestDto.getEmail();
        String password = userLoginRequestDto.getPassword();

        JWTResponse jwtResponse = loginService.login(email, password, "168.0.0.1");

        return jwtResponse;
    }
}
