package com.theinfinity007.user_service.config;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "application.email")
@Getter
@Setter
public class EmailConfig {
    private String baseUrl;
    private String apiKey;
    private String username;
    private String senderEmail;
    private String localPart;
    private String brandName;
}
