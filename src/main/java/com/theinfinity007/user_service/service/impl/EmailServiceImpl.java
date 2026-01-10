package com.theinfinity007.user_service.service.impl;

import com.theinfinity007.user_service.enums.OtpAction;
import com.theinfinity007.user_service.integration.outbound.serviceclient.mailgun.EmailProvider;
import com.theinfinity007.user_service.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final EmailProvider emailProvider;

    @Override
    public void send(String email, OtpAction action, String otpCode) {
        emailProvider.sendSimpleMessage(email, otpCode);
    }
}
