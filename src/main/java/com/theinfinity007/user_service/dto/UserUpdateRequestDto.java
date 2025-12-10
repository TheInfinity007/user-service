package com.theinfinity007.user_service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
public class UserUpdateRequestDto {
    @NotBlank(message = "is required and must not be blank")
    private String firstName;

    @NotBlank(message = "is required and must not be blank")
    private String lastName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "is required and must not be blank")
    private Date birthday;

    @NotBlank(message = "is required and must not be blank")
    private String gender;

    @NotBlank(message = "is required and must not be blank")
    private String title;
}
