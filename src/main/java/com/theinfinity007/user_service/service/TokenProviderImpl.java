package com.theinfinity007.user_service.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.theinfinity007.user_service.config.JWTConfig;

import com.theinfinity007.user_service.dto.JWTResponse;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;
import java.util.Map;

@Component
public class TokenProviderImpl implements TokenProvider{

    private final JWTConfig jwtConfig;

    public TokenProviderImpl(JWTConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    @Override
    public JWTResponse signJWT(Map<String, Object> payload, String subject) {
        Algorithm algorithm = Algorithm.HMAC256(jwtConfig.getJwtSecret());

        Date expiresAt = Date.from(Instant.now().plusSeconds(jwtConfig.getJwtExpiry()));

        try {
            String accessToken = JWT.create()
                    .withIssuer(jwtConfig.getJwtIssuer())
                    .withSubject(subject)
                    .withPayload(payload)
                    .withExpiresAt(expiresAt)
                    .sign(algorithm);

            JWTResponse jwtResponse = new JWTResponse();
            jwtResponse.setAccessToken(accessToken);
            jwtResponse.setTokenType("Bearer");
            jwtResponse.setExpiresIn(jwtConfig.getJwtExpiry());

            return jwtResponse;
        } catch (JWTCreationException e) {
            System.out.println("Error in JWT Creation " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
