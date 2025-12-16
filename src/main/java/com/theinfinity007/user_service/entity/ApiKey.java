package com.theinfinity007.user_service.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiKey extends BaseEntity{
    private String name;
    private String key;
    private String scope;

    public ApiKey(String name, String key, String scope) {
        this.name = name;
        this.key = key;
        this.scope = scope;
    }
}
