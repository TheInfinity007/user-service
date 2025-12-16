package com.theinfinity007.user_service.entity;

import static com.theinfinity007.user_service.constant.DatabaseConstant.*;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = TABLE_API_KEY, indexes = {
        @Index(name = INDEX_API_KEY_UNIQUE, columnList = COLUMN_NAME_API_KEY, unique = true)
})
public class ApiKey extends BaseEntity{

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = COLUMN_NAME_API_KEY, nullable = false)
    private String key;

    @Column(name = "scopes", nullable = false)
    private String scopes;

    @Column(name = "expired_at", nullable = false)
    private Date expiredAt;

    public String[] getScopes() {
        return scopes.split(",");
    }

    public void setScopes(String[] scopes) {
        this.scopes = String.join(",", scopes);
    }

}
