package com.theinfinity007.user_service.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {

    @Value("${application.security.permitted.url-pattern}")
    private final String[] permittedUrlPattern;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new Argon2PasswordEncoder(32, 64, 2, 1 << 16, 4);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)  // Disable CSRF for REST API
                .authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers(permittedUrlPattern).permitAll()
                                .anyRequest().authenticated()   // Remove the auth
                );

        return http.build();
    }
}
