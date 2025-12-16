package com.theinfinity007.user_service.security.authentication;

import com.theinfinity007.user_service.entity.ApiKey;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class ApiKeyAuthenticationProvider implements AuthenticationProvider {
    private final Log logger = LogFactory.getLog(getClass());
    private final String SPACE = " ";

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        this.logger.trace("ApiKeyAuthenticationProvider authenticate called");

        ApiKeyAuthenticationToken apiKeyAuthenticationToken = (ApiKeyAuthenticationToken) authentication;
        AbstractAuthenticationToken token = validateApiKey(apiKeyAuthenticationToken.getApiKey());

        this.logger.debug("Authenticated api Key");
        return token;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return ApiKeyAuthenticationToken.class.isAssignableFrom(authentication);
    }

    private AbstractAuthenticationToken validateApiKey(String apiKey) {
        if(Objects.equals(apiKey, "sample-test-key")){
            String scope = "fetch-profile";
            ApiKey apiKeyEntity = new ApiKey();
            apiKeyEntity.setKey(apiKey);
            apiKeyEntity.setScopes(new String[]{scope});

            return convertToToken(apiKeyEntity);
        }

        throw new AuthenticationServiceException("Invalid Api key");
    }

    private AbstractAuthenticationToken convertToToken(ApiKey apiKey){
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
        for(String authority: getAuthorities(apiKey)){
            grantedAuthorityList.add(new SimpleGrantedAuthority(authority));
        }

        return new ApiKeyAuthenticationToken(apiKey.getKey(), grantedAuthorityList);
    }

    private String[] getAuthorities(ApiKey apiKey){
        return apiKey.getScopes();
    }
}
