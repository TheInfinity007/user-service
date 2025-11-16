package com.theinfinity007.user_service.service;

import com.theinfinity007.user_service.dto.JWTResponse;

public interface LoginService {
    JWTResponse login(String email, String password, String ip);
}
