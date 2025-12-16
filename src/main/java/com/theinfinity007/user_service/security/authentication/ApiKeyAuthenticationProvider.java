package com.theinfinity007.user_service.security.authentication;

import com.theinfinity007.user_service.entity.ApiKey;
import com.theinfinity007.user_service.exception.ApiKeyNotFoundException;
import com.theinfinity007.user_service.service.ApiKeyService;
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
import java.util.Date;
import java.util.List;

@Component
public class ApiKeyAuthenticationProvider implements AuthenticationProvider {
    private final Log logger = LogFactory.getLog(getClass());
    private final ApiKeyService apiKeyService;
    private final String AUTHORITY_PREFIX = "SCOPE_";

    public ApiKeyAuthenticationProvider(ApiKeyService apiKeyService) {
        this.apiKeyService = apiKeyService;
    }

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

    private AbstractAuthenticationToken validateApiKey(String key) {

        ApiKey apiKey;
        try {
            apiKey = apiKeyService.findByKey(key);
        } catch (ApiKeyNotFoundException ex) {
            logger.debug("Api key does not exist");
            throw new AuthenticationServiceException("Invalid Api key");
        }

        Date currentDate = new Date();
        if (apiKey.getExpiredAt().before(currentDate)) {
            throw new AuthenticationServiceException("Api key has expired");
        }

        return convertToToken(apiKey);
    }

    private AbstractAuthenticationToken convertToToken(ApiKey apiKey){
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
        for(String authority: getAuthorities(apiKey)){
            grantedAuthorityList.add(new SimpleGrantedAuthority(AUTHORITY_PREFIX + authority));
        }

        return new ApiKeyAuthenticationToken(apiKey.getKey(), grantedAuthorityList);
    }

    private String[] getAuthorities(ApiKey apiKey){
        return apiKey.getScopes();
    }
}
