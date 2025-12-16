package com.theinfinity007.user_service.exception;

import org.springframework.http.HttpStatus;

public class ApiKeyNotFoundException extends UserServiceException{

    public ApiKeyNotFoundException(String key) {
        super("ApiKeyNotFoundError", "ApiKey not found with key " + key, HttpStatus.NOT_FOUND.value());
    }
}
