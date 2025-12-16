package com.theinfinity007.user_service.dao;

import com.theinfinity007.user_service.entity.ApiKey;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ApiKeyDaoImpl implements ApiKeyDao {

    private final EntityManager entityManager;

    @Override
    public ApiKey create(ApiKey apiKey) {
        entityManager.persist(apiKey);
        return apiKey;
    }
}
