package com.theinfinity007.user_service.exception.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.theinfinity007.user_service.constant.AppConstant;
import com.theinfinity007.user_service.dto.response.ErrorResponseDto;
import com.theinfinity007.user_service.utils.CommonUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthenticationExceptionHandler implements AuthenticationEntryPoint {

    private final BearerTokenAuthenticationEntryPoint delegate = new BearerTokenAuthenticationEntryPoint();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        delegate.commence(request, response, authException);

        String timestamp = CommonUtil.getCurrentTimestamp();
        String errorMessage = getErrorMessage(authException);

        response.setContentType(AppConstant.APPLICATION_JSON);
        ErrorResponseDto responseBody = new ErrorResponseDto(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase(), errorMessage, timestamp);
        response.getWriter().write(objectMapper.writeValueAsString(responseBody));
    }

    private String getErrorMessage(AuthenticationException ex) {
        String message = ex.getMessage().toLowerCase();
        String result = "Invalid Token";

        if (message.contains("authentication is required")) {
            result = "Missing Authentication";
        }

        if (message.contains("jwt expired")) {
            result = "Expired token";
        }

        return result;
    }
}
