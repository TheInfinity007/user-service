package com.theinfinity007.user_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResetPasswordRequestDto {
    @NotBlank(message = "is required and must not be blank")
    private String token;

    @NotBlank(message = "is required and must not be blank")
    private String newPassword;

    @NotBlank(message = "is required and must not be blank")
    private String email;
}

