package com.theinfinity007.user_service.exception;

import org.springframework.http.HttpStatus;

public class InvalidOtpException extends UserServiceException{

    public InvalidOtpException() {
        super("InvalidOtpError", "InvalidOtpError", HttpStatus.BAD_REQUEST.value());
    }
}
