package com.theinfinity007.user_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class UserCreateRequestDto {

    @NotBlank(message = "is required and must not be blank")
    private String email;

    @NotBlank(message = "is required and must not be blank")
    private String password;

    @NotBlank(message = "is required and must not be blank")
    private String firstName;
    private String lastName;
    private String gender;
    private String title;
    private Date birthday;
}
