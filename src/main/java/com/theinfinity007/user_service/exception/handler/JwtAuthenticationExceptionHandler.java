package com.theinfinity007.user_service.exception.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.theinfinity007.user_service.constant.AppConstant;
import com.theinfinity007.user_service.dto.ErrorResponseDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
public class JwtAuthenticationExceptionHandler implements AuthenticationEntryPoint {

    private final BearerTokenAuthenticationEntryPoint delegate = new BearerTokenAuthenticationEntryPoint();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        delegate.commence(request, response, authException);

        String timestamp = Instant.now().truncatedTo(ChronoUnit.MILLIS).toString();
        String errorMessage = authException.getMessage().toLowerCase().contains("jwt expired") ? "Expired token" : "Invalid token";

        response.setContentType(AppConstant.APPLICATION_JSON);
        ErrorResponseDto responseBody = new ErrorResponseDto(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase(), errorMessage, timestamp);
        response.getWriter().write(objectMapper.writeValueAsString(responseBody));
    }
}
