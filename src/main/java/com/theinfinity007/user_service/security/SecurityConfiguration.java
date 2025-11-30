package com.theinfinity007.user_service.security;

import com.theinfinity007.user_service.config.JWTConfig;
import com.theinfinity007.user_service.exception.handler.JwtAuthenticationExceptionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;


import javax.crypto.spec.SecretKeySpec;

@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {

    @Value("${application.security.permitted.url-pattern}")
    private final String[] permittedUrlPattern;

    @Value("${application.security.anonymous.access-route}")
    private final String[] anonymousAccessRoutes;

    private final JWTConfig jwtConfig;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new Argon2PasswordEncoder(32, 64, 2, 1 << 16, 4);
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        String secretKey = jwtConfig.getJwtSecret();
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), jwtConfig.getAlgorithm());
        return NimbusJwtDecoder.withSecretKey(secretKeySpec).build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)  // Disable CSRF for REST API
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers(permittedUrlPattern).permitAll();  // Allow access to

                    for (String route : anonymousAccessRoutes) {    // Allow anonymous access to specific endpoint
                        String[] parts = route.split(":");
                        String method = parts[0];
                        String path = parts[1];

                        auth.requestMatchers(method, path).permitAll();
                    }

                    auth.anyRequest().authenticated();
                })
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(Customizer.withDefaults())
                        .authenticationEntryPoint(new JwtAuthenticationExceptionHandler())    // handle token expiress
                );

        return http.build();
    }
}
