package com.theinfinity007.user_service.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.Date;

@Data
public class ApiKeyCreateRequestDto {
    @NotBlank(message = "is required and must not be blank")
    private String description;

    @NotEmpty(message = "is required")
    @Size(min = 1, max = 10)
    private String[] scopes;

    @NotNull
    @Future
    private Date expiry;
}
