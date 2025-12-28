package com.theinfinity007.user_service.dto.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordRequestDto {

    @NotBlank(message = "is required and must not be blank")
    @Size(min = 8, max = 20)
    private String password;
}
