package com.theinfinity007.user_service.config;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OtpLengthConfig {
    private int defaultLength;
    private int passwordReset;
}
