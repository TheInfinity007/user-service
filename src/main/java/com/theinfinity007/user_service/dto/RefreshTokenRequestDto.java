package com.theinfinity007.user_service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RefreshTokenRequestDto {

    @NotBlank(message = "is required and must not be blank")
    private String token;
}
