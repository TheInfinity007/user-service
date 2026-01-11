package com.theinfinity007.user_service.dao.impl;

import com.theinfinity007.user_service.dao.TemplateDAO;
import com.theinfinity007.user_service.entity.Template;
import com.theinfinity007.user_service.enums.OtpAction;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TemplateDAOImpl implements TemplateDAO {
    private final EntityManager entityManager;

    @Override
    public Template get(String type, String code, String lang) {
        TypedQuery<Template> query = entityManager.createQuery("FROM Template t WHERE t.type=:type AND t.code=:code AND t.lang=:lang", Template.class);
        query.setParameter("type", type);
        query.setParameter("code", code);
        query.setParameter("lang", lang);

        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
