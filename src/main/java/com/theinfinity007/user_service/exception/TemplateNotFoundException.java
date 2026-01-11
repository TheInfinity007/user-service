package com.theinfinity007.user_service.exception;

import org.springframework.http.HttpStatus;

public class TemplateNotFoundException extends UserServiceException {
    public TemplateNotFoundException() {
        super("TemplateNotFoundError", "Template Not Found", HttpStatus.NOT_FOUND.value());
    }
}
