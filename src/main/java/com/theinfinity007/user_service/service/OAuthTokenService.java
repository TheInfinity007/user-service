package com.theinfinity007.user_service.service;

import com.theinfinity007.user_service.dto.JWTResponse;

import java.util.Map;

public interface OAuthTokenService {
    JWTResponse generateToken(Map<String, String> payload, Long id);
}
