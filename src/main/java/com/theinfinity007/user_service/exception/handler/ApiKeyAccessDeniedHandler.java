package com.theinfinity007.user_service.exception.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class ApiKeyAccessDeniedHandler implements AccessDeniedHandler {

    public static final String INSUFFICIENT_SCOPE = "insufficient_scope";

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) {
        Map<String, String> parameters = new LinkedHashMap<>();

        parameters.put("error", INSUFFICIENT_SCOPE);

        String wwwAuthenticate = computeWWWAuthenticateHeaderValue(parameters);

        response.addHeader(HttpHeaders.WWW_AUTHENTICATE, wwwAuthenticate);
        response.setStatus(HttpStatus.FORBIDDEN.value());
    }

    private String computeWWWAuthenticateHeaderValue(Map<String, String> parameters) {

        StringBuilder wwwAuthenticate = new StringBuilder();
        wwwAuthenticate.append("APIKey");
        if (!parameters.isEmpty()) {
            wwwAuthenticate.append(" ");
            int i = 0;
            for (Map.Entry<String, String> entry : parameters.entrySet()) {
                wwwAuthenticate.append(entry.getKey()).append("=\"").append(entry.getValue()).append("\"");
                if (i != parameters.size() - 1) {
                    wwwAuthenticate.append(", ");
                }
                i++;
            }
        }
        return wwwAuthenticate.toString();

    }
}
