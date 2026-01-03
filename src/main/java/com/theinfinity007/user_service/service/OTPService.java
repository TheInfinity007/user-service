package com.theinfinity007.user_service.service;

import com.theinfinity007.user_service.entity.Otp;

public interface OTPService {
    Otp create(Otp otp);

    Otp get(String email, String code);

    Otp update(Otp otp);
}
