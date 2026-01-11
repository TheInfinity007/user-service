package com.theinfinity007.user_service.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
public class UserServiceException extends RuntimeException{

    private final String errorCode;
    private final Integer statusCode;

    public UserServiceException(String errorCode, String message, int statusCode) {
        super(message);
        this.errorCode = errorCode;
        this.statusCode = statusCode;
    }

    public UserServiceException(HttpStatus httpStatus, String message) {
        super(message);
        this.errorCode = httpStatus.getReasonPhrase();
        this.statusCode = httpStatus.value();
    }
}
