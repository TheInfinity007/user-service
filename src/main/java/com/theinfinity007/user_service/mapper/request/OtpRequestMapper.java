package com.theinfinity007.user_service.mapper.request;

import com.theinfinity007.user_service.config.ExpiryConfig;
import com.theinfinity007.user_service.config.OtpConfig;
import com.theinfinity007.user_service.constant.AppConstant;
import com.theinfinity007.user_service.entity.Otp;
import com.theinfinity007.user_service.entity.User;
import com.theinfinity007.user_service.enums.OtpAction;
import com.theinfinity007.user_service.enums.OtpStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class OtpRequestMapper {

    private final OtpConfig otpConfig;

    public Otp execute(String email, OtpAction action, String otpCode, User user, String channel) {
        Otp otp = new Otp();
        otp.setUuid(UUID.randomUUID().toString());
        otp.setOtpCode(otpCode);
        otp.setAction(action.name());
        otp.setEmail(email);
        otp.setUser(user);
        otp.setChannel(channel);
        otp.setStatus(OtpStatus.PENDING);
        otp.setExpiredAt(getExpiry(action));

        return otp;
    }

    private Date getExpiry(OtpAction action) {
        return Date.from(
                Instant.now().plus(
                        otpConfig.getExpiry().getExpiryByAction(action),
                        ChronoUnit.MINUTES
                )
        );
    }
}
