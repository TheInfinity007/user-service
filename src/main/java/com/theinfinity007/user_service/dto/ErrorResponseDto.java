package com.theinfinity007.user_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponseDto {
    private int statusCode;
    private String errorCode;
    private String message;
    private String timestamp;
}
