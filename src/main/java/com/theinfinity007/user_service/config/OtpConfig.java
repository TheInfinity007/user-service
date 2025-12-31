package com.theinfinity007.user_service.config;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component
@ConfigurationProperties(prefix = "application.otp")
@Getter
@Setter
public class OtpConfig {
    private ExpiryConfig expiry;
    private OtpLengthConfig length;
}
