package com.theinfinity007.user_service.service.impl;

import com.theinfinity007.user_service.dao.UserDAO;
import com.theinfinity007.user_service.entity.User;
import com.theinfinity007.user_service.exception.UserAlreadyExistsException;
import com.theinfinity007.user_service.exception.UserNotFoundException;
import com.theinfinity007.user_service.service.TokenService;
import com.theinfinity007.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;
    private final TokenService tokenService;

    @Override
    @Transactional
    public User create(User user) {

        try {
           return userDAO.create(user);
        } catch (DataIntegrityViolationException e) {

            if(e.getMessage().contains("idx_email")){
                throw new UserAlreadyExistsException(user.getEmail());
            }

            throw new IllegalStateException("Unable to create user due to data constraint violation", e);
        }
    }

    @Override
    @org.springframework.transaction.annotation.Transactional
    public User update(User user) {
        return userDAO.update(user);
    }

    @Override
    public User findByEmail(String email) {
        User user = userDAO.findByEmail(email);

        if (user == null) {
            throw new UserNotFoundException(email);
        }
        return user;
    }

    @Override
    public User findById(Long id) {
        User user = userDAO.findById(id);

        if (user == null) {
            throw new UserNotFoundException(id);
        }

        return user;
    }

    @Override
    @Transactional
    public void delete(User user) {
        tokenService.deleteByUserId(user.getId());
        userDAO.delete(user);
    }
}
