package com.theinfinity007.user_service.dao;

import com.theinfinity007.user_service.entity.Token;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;


@Repository
public class TokenDAOImpl implements TokenDAO {

    private final EntityManager entityManager;

    public TokenDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Token create(Token token) {
        entityManager.persist(token);
        return token;
    }

    @Override
    public Token findByToken(String token) {
        TypedQuery<Token> query = entityManager.createQuery("FROM Token WHERE token = :token and deletedAt IS NULL", Token.class);
        query.setParameter("token" , token);

        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Token save(Token token) {
        return entityManager.merge(token);
    }
}
