package com.theinfinity007.user_service.entity;

import com.theinfinity007.user_service.enums.OtpStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "otps")
public class Otp extends BaseEntity {

    @Column(name = "otp_code", nullable = false)
    private String otpCode;

    @Column(name = "uuid", nullable = false)
    private String uuid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_otp_user"))
    private User user;

    @Column(name = "channel", nullable = false)
    private String channel;

    @Column(name = "action", nullable = false)
    private String action;
    private String email;

    @Column(name = "status", nullable = false)
    private OtpStatus status;

    @Column(name = "expired_at", nullable = false)
    private Date expiredAt;
}
