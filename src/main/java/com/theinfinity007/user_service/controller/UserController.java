package com.theinfinity007.user_service.controller;

import com.theinfinity007.user_service.config.OtpConfig;
import com.theinfinity007.user_service.constant.AppConstant;
import com.theinfinity007.user_service.dto.request.*;
import com.theinfinity007.user_service.dto.response.ForgotPasswordResponseDto;
import com.theinfinity007.user_service.dto.response.JWTResponse;
import com.theinfinity007.user_service.dto.response.UserResponseDto;
import com.theinfinity007.user_service.entity.Otp;
import com.theinfinity007.user_service.entity.User;
import com.theinfinity007.user_service.enums.OtpAction;
import com.theinfinity007.user_service.enums.OtpStatus;
import com.theinfinity007.user_service.exception.*;
import com.theinfinity007.user_service.mapper.request.OtpRequestMapper;
import com.theinfinity007.user_service.mapper.request.UserRequestMapper;
import com.theinfinity007.user_service.mapper.response.UserResponseMapper;
import com.theinfinity007.user_service.service.LoginService;
import com.theinfinity007.user_service.service.OTPService;
import com.theinfinity007.user_service.service.UserService;
import com.theinfinity007.user_service.utils.CommonUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "User")
@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final LoginService loginService;
    private final OTPService otpService;
    private final UserRequestMapper userRequestMapper;
    private final OtpRequestMapper otpRequestMapper;
    private final OtpConfig otpConfig;

    @Operation(security = {@SecurityRequirement(name = AppConstant.AUTH_STRATEGY_API_KEY)})
    @GetMapping("/{email}")
    @PreAuthorize("hasAuthority('read:user')")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable String email) {
        User user = userService.findByEmail(email);

        UserResponseDto response = UserResponseMapper.populate(user);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(security = {@SecurityRequirement(name = AppConstant.AUTH_STRATEGY_API_KEY)})
    @PostMapping("")
    @PreAuthorize("hasAuthority('create:user')")
    public ResponseEntity<UserResponseDto> signup(@RequestBody @Valid UserCreateRequestDto userCreateRequestDto) {
        User user = userRequestMapper.populate(userCreateRequestDto);

        User createdUser = userService.create(user);

        UserResponseDto response = UserResponseMapper.populate(createdUser);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(security = {@SecurityRequirement(name = AppConstant.AUTH_STRATEGY_API_KEY)})
    @PostMapping("/login")
    @PreAuthorize("hasAuthority('login:user')")
    public ResponseEntity<JWTResponse> login(@RequestBody @Valid UserLoginRequestDto userLoginRequestDto, @RequestHeader HttpHeaders headers, HttpServletRequest request) {

        String email = userLoginRequestDto.getEmail();
        String password = userLoginRequestDto.getPassword();

        String clientIp = CommonUtil.getClientIPAddress(headers.get("x-forwarded-for"), request);

        JWTResponse jwtResponse = loginService.login(email, password, clientIp);
        return ResponseEntity.ok().body(jwtResponse);
    }

    @Operation(security = {@SecurityRequirement(name = AppConstant.AUTH_STRATEGY_OAUTH)})
    @GetMapping("/me")
    @PreAuthorize("hasAuthority('SCOPE_openid')")
    public ResponseEntity<UserResponseDto> fetchProfile(@AuthenticationPrincipal Jwt jwt) {

        Long userId = Long.valueOf(jwt.getClaim("sub"));

        User user = userService.findById(userId);

        UserResponseDto response = UserResponseMapper.populate(user);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(security = {@SecurityRequirement(name = AppConstant.AUTH_STRATEGY_OAUTH)})
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

    @Operation(security = {@SecurityRequirement(name = AppConstant.AUTH_STRATEGY_API_KEY)})
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

    @Operation(security = {@SecurityRequirement(name = AppConstant.AUTH_STRATEGY_OAUTH)})
    @DeleteMapping("/me")
    @PreAuthorize("hasAuthority('SCOPE_openid')")
    public ResponseEntity<Void> deleteUser(@AuthenticationPrincipal Jwt jwt){
        Long userId = CommonUtil.getUserIdFromToken(jwt);

        User user = userService.findById(userId);

        userService.delete(user);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @Operation(
            security = {@SecurityRequirement(name = AppConstant.AUTH_STRATEGY_OAUTH)},
            summary = "Change password using JWT"
    )
    @PutMapping("/me/password")
    @PreAuthorize("hasAuthority('SCOPE_openid')")
    public ResponseEntity<Void> changePassword(@AuthenticationPrincipal Jwt jwt, @RequestBody @Valid ChangePasswordRequestDto changePasswordRequestDto) {
        Long userId = CommonUtil.getUserIdFromToken(jwt);

        String password = changePasswordRequestDto.getPassword();

        User user = userService.findById(userId);

        boolean isSame = loginService.validatePassword(user.getPassword(), password);
        if (isSame) {
            throw new PasswordConflictException();
        }

        userService.changePassword(user, password);

        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @Operation(
            security = {@SecurityRequirement(name = AppConstant.AUTH_STRATEGY_API_KEY)},
            summary = "Initiate password reset process")
    @PostMapping("/forgot-password")
    @PreAuthorize("hasAuthority('forgot-password')")
    public ResponseEntity<ForgotPasswordResponseDto> forgotPassword(@RequestBody @Valid ForgotPasswordRequestDto forgotPasswordRequestDto) {
        String email = forgotPasswordRequestDto.getEmail();
        ForgotPasswordResponseDto response = new ForgotPasswordResponseDto();

        try {
            // Validate user existence by email
            userService.findByEmail(email);
        } catch (UserNotFoundException _ex) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }

        String otpCode = CommonUtil.generateRandomString(otpConfig.getLength().getPasswordReset());
        Otp otp = otpRequestMapper.execute(email, OtpAction.RESET_PASSWORD, otpCode, AppConstant.COMMUNICATION_CHANNEL_EMAIL);

        otpService.create(otp);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(
            security = {@SecurityRequirement(name = AppConstant.AUTH_STRATEGY_API_KEY)},
            summary = "Reset Password")
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordRequestDto resetPasswordRequestDto) {

        String email = resetPasswordRequestDto.getEmail();
        String code = resetPasswordRequestDto.getToken();

        Otp otp = otpService.get(email, code);

        if (otp == null) {
            throw new InvalidOtpException();
        }

        boolean isOtpExpired = CommonUtil.checkExpiry(otp.getExpiredAt(), 1000);
        if (isOtpExpired) {
            throw new OtpExpiredException();
        }

        // Check otp status
        if (otp.getStatus() != OtpStatus.NEW) {
            throw new OtpAlreadyUsedException();
        }



        String newPassword = resetPasswordRequestDto.getNewPassword();
        User user = userService.findByEmail(email);

        boolean isSame = loginService.validatePassword(user.getPassword(), newPassword);
        if (isSame) {
            throw new PasswordConflictException();
        }

        // Update otp status
        otp.setStatus(OtpStatus.VERIFIED);
        otpService.update(otp);

        userService.changePassword(user, newPassword);

        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
