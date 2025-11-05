package com.theinfinity007.user_service.dto;

import lombok.Setter;

@Setter
public class UserCreateRequestDto {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String gender;
    private String title;
    private String birthday;
}
