package com.theinfinity007.user_service.exception;

import org.springframework.http.HttpStatus;

public class OtpExpiredException extends UserServiceException{

    public OtpExpiredException() {
        super("OtpExpiredError", "OtpExpiredError", HttpStatus.BAD_REQUEST.value());
    }
}
