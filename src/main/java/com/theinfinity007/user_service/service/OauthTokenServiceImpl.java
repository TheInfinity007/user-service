package com.theinfinity007.user_service.service;

import com.theinfinity007.user_service.dto.JWTResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class OauthTokenServiceImpl implements OAuthTokenService{

    private final TokenProvider tokenProvider;

    @Override
    public JWTResponse generateToken(Map<String, String> payload, Long id) {

        JWTResponse result = tokenProvider.signJWT(payload, id.toString());

        return result;
    }
}
