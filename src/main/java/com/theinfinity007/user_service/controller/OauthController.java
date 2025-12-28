package com.theinfinity007.user_service.controller;

import com.theinfinity007.user_service.dto.JWTResponse;
import com.theinfinity007.user_service.dto.RefreshTokenRequestDto;
import com.theinfinity007.user_service.dto.RevokeTokenRequestDto;
import com.theinfinity007.user_service.entity.Token;
import com.theinfinity007.user_service.entity.User;
import com.theinfinity007.user_service.service.TokenService;
import com.theinfinity007.user_service.service.UserService;
import com.theinfinity007.user_service.utils.CommonUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/oauth")
public class OauthController {

    private final TokenService tokenService;

    @PostMapping("/token")
    @PreAuthorize("hasAuthority('oauth:token')")
    public ResponseEntity<JWTResponse> refreshToken(@RequestBody RefreshTokenRequestDto refreshTokenRequestDto, @RequestParam(name = "grant_type") String grantType, @RequestHeader HttpHeaders headers, HttpServletRequest request) {

        String clientIp = CommonUtil.getClientIPAddress(headers.get("x-forwarded-for"), request);

        String refreshToken = refreshTokenRequestDto.getToken();
        Token token = tokenService.findByRefreshToken(refreshToken);

        User user = token.getUser();

        JWTResponse jwtResponse = tokenService.refreshToken(refreshToken, clientIp, user, token);

        return ResponseEntity.ok().body(jwtResponse);
    }

    @PostMapping("/revoke")
    @PreAuthorize("hasAuthority('revoke:token')")
    public ResponseEntity<Void> revokeToken(@RequestBody RevokeTokenRequestDto revokeTokenRequestDto, @RequestHeader HttpHeaders headers, HttpServletRequest request) {
        String clientIp = CommonUtil.getClientIPAddress(headers.get("x-forwarded-for"), request);
        String token = revokeTokenRequestDto.getToken();

        tokenService.revokeToken(token, clientIp);

        return ResponseEntity.noContent().build();
    }
}
