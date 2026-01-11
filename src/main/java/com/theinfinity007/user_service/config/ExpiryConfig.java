package com.theinfinity007.user_service.config;

import com.theinfinity007.user_service.enums.OtpAction;
import lombok.Getter;
import lombok.Setter;

@Setter
public class ExpiryConfig {
    private int passwordReset;
    private int defaultExpiry;

    public int getExpiryByAction(OtpAction action) {
        return switch (action) {
            case OtpAction.FORGOT_PASSWORD -> passwordReset;
            default -> defaultExpiry;
        };
    }
}
