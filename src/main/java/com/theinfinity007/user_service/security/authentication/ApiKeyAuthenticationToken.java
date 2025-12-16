package com.theinfinity007.user_service.security.authentication;

import lombok.*;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@Getter
public class ApiKeyAuthenticationToken extends AbstractAuthenticationToken {

    private final String apiKey;

    public ApiKeyAuthenticationToken(String apiKey){
        super(null);
        this.apiKey = apiKey;
        setAuthenticated(false);
    }

    public ApiKeyAuthenticationToken(String apiKey, List<GrantedAuthority> authority){
        super(authority);
        this.apiKey = apiKey;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return this.getApiKey();
    }

    @Override
    public Object getPrincipal() {
        return null;
    }
}
