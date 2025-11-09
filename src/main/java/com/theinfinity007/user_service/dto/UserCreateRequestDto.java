package com.theinfinity007.user_service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

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
    private LocalDate birthday;

    // validation group interfaces
    public interface First {}
    public interface Second {}
}
