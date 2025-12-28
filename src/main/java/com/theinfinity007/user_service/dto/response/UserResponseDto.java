package com.theinfinity007.user_service.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserResponseDto {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String title;
    private String gender;
    private Date birthday;
    private Date createdAt;
    private Date modifiedAt;
}
