package com.theinfinity007.user_service.service.impl;

import com.theinfinity007.user_service.dao.OtpDAO;
import com.theinfinity007.user_service.entity.Otp;
import com.theinfinity007.user_service.service.OTPService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OTPServiceImpl implements OTPService {

    private final OtpDAO otpDAO;

    @Override
    @Transactional
    public Otp create(Otp otp) {
        return otpDAO.create(otp);
    }
}
