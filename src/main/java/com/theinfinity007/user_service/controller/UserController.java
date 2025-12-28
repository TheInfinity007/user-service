package com.theinfinity007.user_service.controller;

import com.theinfinity007.user_service.dto.*;
import com.theinfinity007.user_service.entity.User;
import com.theinfinity007.user_service.mapper.request.UserRequestMapper;
import com.theinfinity007.user_service.mapper.response.UserResponseMapper;
import com.theinfinity007.user_service.service.LoginService;
import com.theinfinity007.user_service.service.UserService;
import com.theinfinity007.user_service.utils.CommonUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final LoginService loginService;
    private final UserRequestMapper userRequestMapper;

    @GetMapping("/{email}")
    @PreAuthorize("hasAuthority('read:user')")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable String email) {
        User user = userService.findByEmail(email);

        UserResponseDto response = UserResponseMapper.populate(user);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("")
    @PreAuthorize("hasAuthority('create:user')")
    public ResponseEntity<UserResponseDto> signup(@RequestBody @Valid UserCreateRequestDto userCreateRequestDto) {

        System.out.println("userCreateRequestDto.getFirstName() = " + userCreateRequestDto.getFirstName());
        User user = userRequestMapper.populate(userCreateRequestDto);

        User createdUser = userService.create(user);

        UserResponseDto response = UserResponseMapper.populate(createdUser);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    @PreAuthorize("hasAuthority('login:user')")
    public ResponseEntity<JWTResponse> login(@RequestBody @Valid UserLoginRequestDto userLoginRequestDto, @RequestHeader HttpHeaders headers, HttpServletRequest request) {

        String email = userLoginRequestDto.getEmail();
        String password = userLoginRequestDto.getPassword();

        String clientIp = CommonUtil.getClientIPAddress(headers.get("x-forwarded-for"), request);

        JWTResponse jwtResponse = loginService.login(email, password, clientIp);
        return ResponseEntity.ok().body(jwtResponse);
    }

    @GetMapping("/me")
    @PreAuthorize("hasAuthority('SCOPE_openid')")
    public ResponseEntity<UserResponseDto> fetchProfile(@AuthenticationPrincipal Jwt jwt) {

        Long userId = Long.valueOf(jwt.getClaim("sub"));

        User user = userService.findById(userId);

        UserResponseDto response = UserResponseMapper.populate(user);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping(value = "/me")
    @PreAuthorize("hasAuthority('SCOPE_openid')")
    public ResponseEntity<UserResponseDto> updateUser(@AuthenticationPrincipal Jwt jwt, @Valid @RequestBody UserUpdateRequestDto payload){
        Long userId = CommonUtil.getUserIdFromToken(jwt);

        // Validate user existence
        User user = userService.findById(userId);

        user = userRequestMapper.populate(payload, user);

        user = userService.update(user);

        UserResponseDto response = UserResponseMapper.populate(user);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping(value = "/{userId}")
    @PreAuthorize("hasAuthority('update:user')")
    public ResponseEntity<UserResponseDto> updateUserById(@PathVariable Long userId, @Valid @RequestBody UserUpdateRequestDto payload){

        // Validate user existence
        User user = userService.findById(userId);

        user = userRequestMapper.populate(payload, user);

        user = userService.update(user);

        UserResponseDto response = UserResponseMapper.populate(user);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/me")
    @PreAuthorize("hasAuthority('SCOPE_openid')")
    public ResponseEntity<Void> deleteUser(@AuthenticationPrincipal Jwt jwt){
        Long userId = CommonUtil.getUserIdFromToken(jwt);

        User user = userService.findById(userId);

        userService.delete(user);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
