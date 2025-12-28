package com.theinfinity007.user_service.exception;

import org.springframework.http.HttpStatus;

public class PasswordConflictException extends UserServiceException{
    public PasswordConflictException() {
        super("PasswordConflictException", "New Password cannot be same as existing password.", HttpStatus.BAD_REQUEST.value());
    }
}
