package com.theinfinity007.user_service.entity;

import com.theinfinity007.user_service.enums.OtpStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import static com.theinfinity007.user_service.constant.DatabaseConstant.*;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "otps", indexes = {
        @Index(name = "idx_email_code_createdAt", columnList = COLUMN_NAME_EMAIL + "," + COLUMN_NAME_OTP_CODE + "," + COLUMN_NAME_CREATED_AT)
})
public class Otp extends BaseEntity {

    @Column(name = COLUMN_NAME_OTP_CODE, nullable = false)
    private String otpCode;

    @Column(name = "uuid", nullable = false)
    private String uuid;

    @Column(name = "channel", nullable = false)
    private String channel;

    @Column(name = "action", nullable = false)
    private String action;

    @Column(name = COLUMN_NAME_EMAIL)
    private String email;

    @Column(name = COLUMN_NAME_STATUS, nullable = false)
    @Enumerated(EnumType.STRING)
    private OtpStatus status;

    @Column(name = "expired_at", nullable = false)
    private Date expiredAt;
}
