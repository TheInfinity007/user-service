package com.theinfinity007.user_service.integration.outbound.serviceclient.mailgun;

import org.springframework.web.client.RestClient;

public interface EmailProvider {
    String sendSimpleMessage(String receipientEmail, String otpCode);
}
