package com.theinfinity007.user_service.dao;

import com.theinfinity007.user_service.entity.ApiKey;

public interface ApiKeyDao {
    ApiKey create(ApiKey apiKey);

    ApiKey findByKey(String key);
}
