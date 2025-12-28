package com.theinfinity007.user_service.exception.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.theinfinity007.user_service.constant.AppConstant;
import com.theinfinity007.user_service.dto.response.ErrorResponseDto;
import com.theinfinity007.user_service.exception.UserServiceException;
import com.theinfinity007.user_service.security.authentication.ApiKeyAuthenticationToken;
import com.theinfinity007.user_service.utils.CommonUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingRequestValueException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

@RestControllerAdvice
@RequiredArgsConstructor
public class DefaultExceptionHandler {

    private final BearerTokenAccessDeniedHandler bearerTokenAccessDeniedHandler = new BearerTokenAccessDeniedHandler();
    private final ObjectMapper objectMapper;
    private final ApiKeyAccessDeniedHandler apiKeyAccessDeniedHandler;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleValidationErrors(MethodArgumentNotValidException ex) {

        String errorMessage = ex.getMessage();

        for(ObjectError error: ex.getBindingResult().getAllErrors()){
            String fieldName =((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errorMessage = fieldName + " " + message;
            break;
        }

        String timestamp = CommonUtil.getCurrentTimestamp();
        int statusCode = HttpStatus.BAD_REQUEST.value();
        ErrorResponseDto response = new ErrorResponseDto(statusCode, HttpStatus.BAD_REQUEST.getReasonPhrase(), errorMessage, timestamp);
        return ResponseEntity.status(statusCode).body(response);
    }

    // Exception handler for the request query params
    @ExceptionHandler({MissingServletRequestParameterException.class, MissingRequestHeaderException.class})
    public ResponseEntity<ErrorResponseDto> handleQueryParamValidationErrors(MissingRequestValueException ex){
        String errorMessage = ex.getMessage();

        String timestamp = CommonUtil.getCurrentTimestamp();
        int statusCode = HttpStatus.BAD_REQUEST.value();
        ErrorResponseDto response = new ErrorResponseDto(statusCode, HttpStatus.BAD_REQUEST.getReasonPhrase(), errorMessage, timestamp);
        return ResponseEntity.status(statusCode).body(response);
    }

    @ExceptionHandler(UserServiceException.class)
    public ResponseEntity<?> handleUserServiceException(UserServiceException ex){

        String timestamp = CommonUtil.getCurrentTimestamp();
        ErrorResponseDto response = new ErrorResponseDto(ex.getStatusCode(), ex.getErrorCode(), ex.getMessage(), timestamp);

        return ResponseEntity.status(ex.getStatusCode()).body(response);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleUncaughtException(HttpMessageNotReadableException ex) {
        String timestamp = CommonUtil.getCurrentTimestamp();

        String errorMessage = ex.getMessage();


        int statusCode = HttpStatus.BAD_REQUEST.value();
        String errorCode = HttpStatus.BAD_REQUEST.getReasonPhrase();

        Throwable cause = ex.getCause();
        if (cause instanceof UnrecognizedPropertyException upe) {
            errorMessage = "Invalid property: " +  "\"" + upe.getPropertyName() + "\"";
        }


        ErrorResponseDto response = new ErrorResponseDto(statusCode, errorCode, errorMessage, timestamp);
        return ResponseEntity.status(statusCode).body(response);
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public void handleAuthorizationDeniedException(HttpServletRequest request, HttpServletResponse response, AuthorizationDeniedException ex) throws IOException {

        String timestamp = CommonUtil.getCurrentTimestamp();
        String errorMessage = ex.getMessage();

        if (request.getUserPrincipal() instanceof ApiKeyAuthenticationToken) {
            apiKeyAccessDeniedHandler.handle(request, response, ex);
            errorMessage = "This API key does not have permission for this operation.";
        } else if (request.getUserPrincipal() instanceof JwtAuthenticationToken) {
            bearerTokenAccessDeniedHandler.handle(request, response, ex);
            errorMessage = "The request requires higher privileges than provided by the access token.";
        }

        response.setContentType(AppConstant.APPLICATION_JSON);
        response.setStatus(HttpStatus.FORBIDDEN.value());

        ErrorResponseDto responseBody = new ErrorResponseDto(HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN.getReasonPhrase(), errorMessage, timestamp);
        response.getWriter().write(objectMapper.writeValueAsString(responseBody));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleUncaughtException(Exception ex) {
        String timestamp = CommonUtil.getCurrentTimestamp();

        int statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
        String errorCode = HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase();
        String errorMessage = ex.getMessage();

        ErrorResponseDto response = new ErrorResponseDto(statusCode, errorCode, errorMessage, timestamp);

        return ResponseEntity.status(statusCode).body(response);
    }
}
