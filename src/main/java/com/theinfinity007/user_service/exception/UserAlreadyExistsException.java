package com.theinfinity007.user_service.exception;

public class UserAlreadyExistsException extends UserServiceException {

    public UserAlreadyExistsException(String email) {
        super("UserAlreadyExistsException", "User with email " + email + " already exists", 409);
    }
}
