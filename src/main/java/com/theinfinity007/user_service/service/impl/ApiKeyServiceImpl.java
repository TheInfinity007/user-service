package com.theinfinity007.user_service.service.impl;

import com.theinfinity007.user_service.constant.DatabaseConstant;
import com.theinfinity007.user_service.dao.ApiKeyDao;
import com.theinfinity007.user_service.entity.ApiKey;
import com.theinfinity007.user_service.exception.ApiKeyAlreadyExistsException;
import com.theinfinity007.user_service.exception.ApiKeyNotFoundException;
import com.theinfinity007.user_service.service.ApiKeyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApiKeyServiceImpl implements ApiKeyService {

    private final ApiKeyDao apiKeyDao;

    @Override
    @Transactional
    public ApiKey create(ApiKey apiKey) {

        try {
            return apiKeyDao.create(apiKey);
        } catch (DataIntegrityViolationException e) {

            if (e.getMessage().contains(DatabaseConstant.INDEX_API_KEY_UNIQUE)) {
                throw new ApiKeyAlreadyExistsException();
            }

            log.error("Unable to create api key due to data constraint violation", e);
            throw new IllegalStateException("Unable to create api key due to data constraint violation", e);
        }
    }

    @Override
    public ApiKey findByKey(String key) {
        ApiKey apiKey = apiKeyDao.findByKey(key);
        if (apiKey == null) {
            throw new ApiKeyNotFoundException(key);
        }

        return apiKey;
    }
}
