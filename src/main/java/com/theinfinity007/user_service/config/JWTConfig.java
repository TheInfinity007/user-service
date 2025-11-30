package com.theinfinity007.user_service.config;


import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class JWTConfig {

    @Value("${application.jwt.secret}")
    private String jwtSecret;

    @Value("${application.jwt.expiry}")
    private Long jwtExpiry;

    @Value("${application.jwt.issuer}")
    private String jwtIssuer;

    @Value("${application.jwt.algorithm}")
    private String algorithm;

    @Value("${application.jwt.scopes}")
    private String scopes;
}
