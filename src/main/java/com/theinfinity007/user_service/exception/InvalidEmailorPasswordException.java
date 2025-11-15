package com.theinfinity007.user_service.exception;

import org.springframework.http.HttpStatus;

public class InvalidEmailorPasswordException extends UserServiceException{
    public InvalidEmailorPasswordException(){
        super("InvalidEmailorPasswordException", "Wrong email or password", HttpStatus.UNAUTHORIZED.value());
    }
}
