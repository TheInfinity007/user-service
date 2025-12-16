package com.theinfinity007.user_service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Date;

@Data
public class ApiKeyCreateRequestDto {
    @NotBlank(message = "is required and must not be blank")
    private String description;

    @NotBlank(message = "is required and must not be blank")
    private String[] scopes;

    @NotBlank(message = "is required and must not be blank")
    private Date expiredAt;
}
