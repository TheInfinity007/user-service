package com.theinfinity007.user_service.dao;

import com.theinfinity007.user_service.entity.ApiKey;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
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

    @Override
    public ApiKey findByKey(String key) {
        TypedQuery<ApiKey> query = entityManager.createQuery("SELECT a FROM ApiKey a WHERE a.key = :key", ApiKey.class);
        query.setParameter("key", key);

        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
