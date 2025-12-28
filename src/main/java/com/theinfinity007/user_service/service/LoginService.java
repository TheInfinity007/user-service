package com.theinfinity007.user_service.service;

import com.theinfinity007.user_service.dto.response.JWTResponse;

public interface LoginService {
    JWTResponse login(String email, String password, String ip);

    boolean validatePassword(String hashedPassword, String password);
}
