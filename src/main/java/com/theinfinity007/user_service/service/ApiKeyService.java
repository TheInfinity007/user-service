package com.theinfinity007.user_service.service;

import com.theinfinity007.user_service.entity.ApiKey;

public interface ApiKeyService {
    ApiKey create(ApiKey apiKey);

    ApiKey findByKey(String key);
}
