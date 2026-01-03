package com.theinfinity007.user_service.exception;

import org.springframework.http.HttpStatus;

public class OtpAlreadyUsedException extends UserServiceException{

    public OtpAlreadyUsedException() {
        super("OtpAlreadyUsedError", "OtpAlreadyUsedError", HttpStatus.CONFLICT.value());
    }
}
