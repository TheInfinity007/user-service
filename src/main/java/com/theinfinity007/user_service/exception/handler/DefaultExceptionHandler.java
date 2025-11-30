package com.theinfinity007.user_service.exception.handler;

import com.theinfinity007.user_service.dto.ErrorResponseDto;
import com.theinfinity007.user_service.exception.UserServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingRequestValueException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class DefaultExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleValidationErrors(MethodArgumentNotValidException ex) {

        String errorMessage = ex.getMessage();

        for(ObjectError error: ex.getBindingResult().getAllErrors()){
            String fieldName =((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errorMessage = fieldName + " " + message;
            break;
        }

        String timestamp = Instant.now().truncatedTo(ChronoUnit.MILLIS).toString();
        int statusCode = HttpStatus.BAD_REQUEST.value();
        ErrorResponseDto response = new ErrorResponseDto(statusCode, HttpStatus.BAD_REQUEST.getReasonPhrase(), errorMessage, timestamp);
        return ResponseEntity.status(statusCode).body(response);
    }

    // Exception handler for the request query params
    @ExceptionHandler({MissingServletRequestParameterException.class, MissingRequestHeaderException.class})
    public ResponseEntity<ErrorResponseDto> handleQueryParamValidationErrors(MissingRequestValueException ex){
        String errorMessage = ex.getMessage();

        String timestamp = Instant.now().truncatedTo(ChronoUnit.MILLIS).toString();
        int statusCode = HttpStatus.BAD_REQUEST.value();
        ErrorResponseDto response = new ErrorResponseDto(statusCode, HttpStatus.BAD_REQUEST.getReasonPhrase(), errorMessage, timestamp);
        return ResponseEntity.status(statusCode).body(response);
    }

    @ExceptionHandler(UserServiceException.class)
    public ResponseEntity<?> handleUserServiceException(UserServiceException ex){

        String timestamp = Instant.now().truncatedTo(ChronoUnit.MILLIS).toString();
        ErrorResponseDto response = new ErrorResponseDto(ex.getStatusCode(), ex.getErrorCode(), ex.getMessage(), timestamp);

        return ResponseEntity.status(ex.getStatusCode()).body(response);
    }
}
