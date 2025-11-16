package com.theinfinity007.user_service.entity;

import com.theinfinity007.user_service.enums.TokenType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "tokens", indexes = {
        @Index(name = "idx_token", columnList = "token", unique = true)
})
public class Token extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_refresh_token_user"))
    private User user;

    @Column(name = "token", nullable = false)
    private String token;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private TokenType type;

    @Column(name = "last_used_at")
    private Date lastUsedAt;

    @Column(name = "access_count")
    private int accessCount;

    @Column(name = "ip")
    private String ip;
}
