package com.theinfinity007.user_service.exception;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends UserServiceException{

    public UserNotFoundException(String email) {
        super("UserNotFoundException", "User not found with email " + email, HttpStatus.NOT_FOUND.value());
    }

    public UserNotFoundException(Long  id) {
        super("UserNotFoundException", "User not found with id " + id, HttpStatus.NOT_FOUND.value());
    }
}
