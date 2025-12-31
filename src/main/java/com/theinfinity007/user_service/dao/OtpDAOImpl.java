package com.theinfinity007.user_service.dao;

import com.theinfinity007.user_service.entity.Otp;
import jakarta.persistence.EntityManager;
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
}
