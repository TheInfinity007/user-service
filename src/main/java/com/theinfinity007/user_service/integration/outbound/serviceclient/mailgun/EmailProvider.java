package com.theinfinity007.user_service.integration.outbound.serviceclient.mailgun;

import org.springframework.web.client.RestClient;

public interface EmailProvider {

    String send(String receipientEmail, String subject, String html, String from, String text);
}
