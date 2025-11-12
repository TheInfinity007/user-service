package com.theinfinity007.user_service.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class UserResponseDto {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String title;
    private String gender;
    private LocalDate birthday;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
