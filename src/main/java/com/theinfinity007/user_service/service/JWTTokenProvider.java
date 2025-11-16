package com.theinfinity007.user_service.service;

import com.theinfinity007.user_service.dto.JWTResponse;

import java.util.Map;

public interface JWTTokenProvider {
    JWTResponse signJWT(Map<String, Object> payload, String subject);
}
