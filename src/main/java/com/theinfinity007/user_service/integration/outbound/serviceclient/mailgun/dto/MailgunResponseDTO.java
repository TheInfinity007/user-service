package com.theinfinity007.user_service.integration.outbound.serviceclient.mailgun.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MailgunResponseDTO {
    private String id;
    private String message;
}
