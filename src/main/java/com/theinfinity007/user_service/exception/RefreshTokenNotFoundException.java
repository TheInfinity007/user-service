package com.theinfinity007.user_service.exception;

import org.springframework.http.HttpStatus;

public class RefreshTokenNotFoundException extends UserServiceException{
    public RefreshTokenNotFoundException(){
        super("RefreshTokenNotFoundException", "Refresh token not found", HttpStatus.NOT_FOUND.value());
    }
}
