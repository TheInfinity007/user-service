package com.theinfinity007.user_service.dao;

import com.theinfinity007.user_service.entity.Token;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public class TokenDAOImpl implements TokenDAO {

    private final EntityManager entityManager;

    public TokenDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public Token create(Token token) {
        entityManager.persist(token);
        return token;
    }
}
