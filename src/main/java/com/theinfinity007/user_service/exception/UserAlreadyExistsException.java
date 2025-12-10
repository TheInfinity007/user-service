package com.theinfinity007.user_service.exception;

import org.springframework.http.HttpStatus;

public class UserAlreadyExistsException extends UserServiceException {

    public UserAlreadyExistsException(String email) {
        super("UserAlreadyExistsError", "User with email " + email + " already exists", HttpStatus.CONFLICT.value());
    }
}
