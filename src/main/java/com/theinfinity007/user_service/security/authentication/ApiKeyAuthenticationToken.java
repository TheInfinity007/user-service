package com.theinfinity007.user_service.security.authentication;

import com.theinfinity007.user_service.entity.ApiKey;
import lombok.*;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@Getter
public class ApiKeyAuthenticationToken extends AbstractAuthenticationToken {

    private final String key;
    private Object principal;

    public ApiKeyAuthenticationToken(String key) {
        super(null);
        this.key = key;
        setAuthenticated(false);
    }

    public ApiKeyAuthenticationToken(String key, List<GrantedAuthority> authority, ApiKey apiKey) {
        super(authority);
        this.key = key;
        this.principal = apiKey;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return this.getKey();
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }
}
