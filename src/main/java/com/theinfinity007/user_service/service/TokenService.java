package com.theinfinity007.user_service.service;

import com.theinfinity007.user_service.dto.JWTResponse;
import com.theinfinity007.user_service.entity.Token;
import com.theinfinity007.user_service.entity.User;

import java.util.Map;

public interface TokenService {

    JWTResponse generateToken(Map<String, Object> payload, User user, String ip);

    Token create(Token token);

    Token save(Token token);

    JWTResponse refreshToken(String token, String ip);
}
