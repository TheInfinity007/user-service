package com.theinfinity007.user_service.dao;

import com.theinfinity007.user_service.entity.Otp;

public interface OtpDAO {
    Otp create(Otp otp);

    Otp get(String email, String code);

    Otp update(Otp otp);
}
