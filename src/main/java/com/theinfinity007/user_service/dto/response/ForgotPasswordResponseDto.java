package com.theinfinity007.user_service.dto.response;

import lombok.Data;

@Data
public class ForgotPasswordResponseDto {
    private String message = "If the email exists, a password reset link has been sent";
}
