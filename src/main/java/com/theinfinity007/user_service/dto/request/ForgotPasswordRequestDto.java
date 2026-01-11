package com.theinfinity007.user_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ForgotPasswordRequestDto {
    @NotBlank(message = "is required and must not be blank")
    private String email;

    private String lang = "en";
}
