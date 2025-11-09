package com.theinfinity007.user_service.exception.handler;

import com.theinfinity007.user_service.exception.UserServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class DefaultExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationErrors(MethodArgumentNotValidException ex) {

        String errorMessage = "";

        for(ObjectError error: ex.getBindingResult().getAllErrors()){
            String fieldName =((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errorMessage = fieldName + " " + message;
            break;
        }

        Map<String, Object> response  = new HashMap<>();
        response.put("statusCode", HttpStatus.BAD_REQUEST.value());
        response.put("message", errorMessage);
        response.put("timestamp", Instant.now().truncatedTo(ChronoUnit.MILLIS).toString());
        response.put("errorCode", "Bad Request");
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(UserServiceException.class)
    public ResponseEntity<?> handleUserServiceException(UserServiceException ex){
        Map<String, Object> response = new HashMap<>();
        response.put("statusCode", ex.getStatusCode());
        response.put("message", ex.getMessage());
        response.put("timestamp", Instant.now().truncatedTo(ChronoUnit.MILLIS).toString());
        response.put("errorCode", ex.getErrorCode());

        return ResponseEntity.status(ex.getStatusCode()).body(response);
    }
}
