package com.theinfinity007.user_service.service;

import com.theinfinity007.user_service.dao.UserDAO;
import com.theinfinity007.user_service.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserDAO userDAO;

    @Override
    @Transactional
    public void create(User user) {
        userDAO.create(user);
    }
}
