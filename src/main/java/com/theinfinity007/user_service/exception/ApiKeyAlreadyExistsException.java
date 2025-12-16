package com.theinfinity007.user_service.exception;

import org.springframework.http.HttpStatus;

public class ApiKeyAlreadyExistsException extends UserServiceException{

    public ApiKeyAlreadyExistsException() {
        super("ApiKeyAlreadyExistsError", "Api Key Already Exists", HttpStatus.CONFLICT.value());
    }

}
