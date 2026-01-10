package com.theinfinity007.user_service.service;

import com.theinfinity007.user_service.enums.OtpAction;

public interface EmailService {
    void send(String email, OtpAction action, String otpCode);
}
