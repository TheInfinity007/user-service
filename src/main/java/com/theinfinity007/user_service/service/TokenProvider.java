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
public class TokenProvider {

    private final JWTConfig jwtConfig;

    public TokenProvider(JWTConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }


    public JWTResponse signJWT(Map<String, String> payload, String subject) {
        Algorithm algorithm = Algorithm.HMAC256(jwtConfig.getJwtSecret());

        Date expiresAt = Date.from(Instant.now().plusSeconds(jwtConfig.getJwtExpiry()));

        try {
            String accessToken = JWT.create()
                    .withIssuer("user-service")
                    .withSubject(subject)
                    .withPayload(payload)
                    .withExpiresAt(expiresAt)
                    .sign(algorithm);

            JWTResponse jwtResponse = new JWTResponse();
            jwtResponse.setAccessToken(accessToken);
            jwtResponse.setTokenType("Bearer");
            jwtResponse.setExpiresIn(jwtConfig.getJwtExpiry());

            return jwtResponse;
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        } catch (JWTCreationException e) {
            throw new RuntimeException(e);
        }
    }
}
