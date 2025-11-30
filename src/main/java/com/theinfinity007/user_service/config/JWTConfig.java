package com.theinfinity007.user_service.config;


import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class JWTConfig {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiry}")
    private Long jwtExpiry;

    @Value("${jwt.issuer}")
    private String jwtIssuer;

    @Value("${jwt.algorithm}")
    private String algorithm;

}
