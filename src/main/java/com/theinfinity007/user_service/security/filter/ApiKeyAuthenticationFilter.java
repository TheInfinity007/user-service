package com.theinfinity007.user_service.security.filter;

import com.theinfinity007.user_service.security.authentication.ApiKeyAuthenticationToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class ApiKeyAuthenticationFilter extends OncePerRequestFilter {

    private final String X_API_KEY_HEADER_NAME = "x-api-key";
    private final AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource = new WebAuthenticationDetailsSource();

    private final AuthenticationManager authenticationManager;

    public ApiKeyAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String apiKey = request.getHeader(X_API_KEY_HEADER_NAME);
        if (apiKey == null) {
            this.logger.trace("Did not process request since did not find api key");
            filterChain.doFilter(request, response);
            return;
        }


        ApiKeyAuthenticationToken authenticationRequest = new ApiKeyAuthenticationToken(apiKey);
        authenticationRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));

        Authentication result = authenticationManager.authenticate(authenticationRequest);

        filterChain.doFilter(request, response);
    }
}
