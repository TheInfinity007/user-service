package com.theinfinity007.user_service.dto.response;

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
