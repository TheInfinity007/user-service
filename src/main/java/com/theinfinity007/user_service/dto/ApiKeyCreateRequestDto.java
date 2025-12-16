package com.theinfinity007.user_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    private Date expiry;
}
