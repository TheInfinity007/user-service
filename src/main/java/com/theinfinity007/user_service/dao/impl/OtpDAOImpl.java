package com.theinfinity007.user_service.dao.impl;

import com.theinfinity007.user_service.dao.OtpDAO;
import com.theinfinity007.user_service.dao.UserDAO;
import com.theinfinity007.user_service.entity.Otp;
import com.theinfinity007.user_service.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OtpDAOImpl implements OtpDAO {

    private final EntityManager entityManager;

    @Override
    public Otp create(Otp otp) {
        entityManager.persist(otp);
        return otp;
    }

    @Override
    public Otp get(String email, String code) {
        TypedQuery<Otp> query =  entityManager.createQuery("FROM Otp o WHERE o.email = :email AND o.otpCode = :code ORDER BY o.createdAt DESC", Otp.class);
        query.setMaxResults(1);
        query.setParameter("email", email);
        query.setParameter("code", code);

        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Otp update(Otp otp) {
        return entityManager.merge(otp);
    }
}
