package com.theinfinity007.user_service.service;

import com.theinfinity007.user_service.dao.UserDAO;
import com.theinfinity007.user_service.entity.User;
import com.theinfinity007.user_service.exception.UserAlreadyExistsException;
import com.theinfinity007.user_service.exception.UserNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserDAO userDAO;

    @Override
    @Transactional
    public User create(User user) {

        try {
           return userDAO.create(user);
        } catch (DataIntegrityViolationException e) {

            if(e.getMessage().contains("idx_email")){
                throw new UserAlreadyExistsException(user.getEmail());
            }

            throw new IllegalStateException("Unable to create user due to data constraint violatoin", e);
        }
    }

    @Override
    public User findByEmail(String email) {
        User user = userDAO.findByEmail(email);

        if (user == null) {
            throw new UserNotFoundException(email);
        }
        return user;
    }
}
