package com.theinfinity007.user_service.security;

import com.theinfinity007.user_service.config.JWTConfig;
import com.theinfinity007.user_service.exception.handler.JwtAuthenticationExceptionHandler;
import com.theinfinity007.user_service.security.filter.ApiKeyAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;


import javax.crypto.spec.SecretKeySpec;

@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {

    @Value("${application.security.permitted.url-pattern}")
    private String[] permittedUrlPattern;

    @Value("${application.security.anonymous.access-route}")
    private String[] anonymousAccessRoutes;

    private final JWTConfig jwtConfig;

    @Autowired
    @Qualifier("requestMappingHandlerMapping")
    private RequestMappingHandlerMapping handlerMapping;

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
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationConfiguration authenticationConfiguration) throws Exception {

        AuthenticationManager authenticationManager = authenticationConfiguration.getAuthenticationManager();

        http
                .csrf(AbstractHttpConfigurer::disable)  // Disable CSRF for REST API
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers(permittedUrlPattern).permitAll();  // Allow access to public endpoints

                    for (String route : anonymousAccessRoutes) {    // Allow anonymous access to specific endpoint
                        String[] parts = route.split(":");

                        String path = parts[1];
                        HttpMethod method = HttpMethod.valueOf(parts[0]);

                        auth.requestMatchers(method, path).permitAll();
                    }

                    auth.anyRequest().authenticated();
                })
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(Customizer.withDefaults())
                        .authenticationEntryPoint(new JwtAuthenticationExceptionHandler())    // handle token expiress
                )
                .addFilterAfter(new ApiKeyAuthenticationFilter(authenticationManager), BearerTokenAuthenticationFilter.class);

        return http.build();
    }
}
