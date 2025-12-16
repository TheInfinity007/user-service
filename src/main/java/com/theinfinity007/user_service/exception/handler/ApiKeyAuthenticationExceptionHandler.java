package com.theinfinity007.user_service.exception.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.theinfinity007.user_service.constant.AppConstant;
import com.theinfinity007.user_service.dto.ErrorResponseDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.theinfinity007.user_service.utils.CommonUtil.getCurrentTimestamp;

@Component
public class ApiKeyAuthenticationExceptionHandler implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        String timestamp = getCurrentTimestamp();
        String errorMessage = authException.getMessage();

        response.setContentType(AppConstant.APPLICATION_JSON);
        ErrorResponseDto responseBody = new ErrorResponseDto(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase(), errorMessage, timestamp);
        response.getWriter().write(objectMapper.writeValueAsString(responseBody));
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
    }
}
