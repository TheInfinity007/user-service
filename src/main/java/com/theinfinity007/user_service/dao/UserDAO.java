package com.theinfinity007.user_service.dao;

import com.theinfinity007.user_service.entity.User;

public interface UserDAO {
    User create(User user);

    User update(User user);

    User findByEmail(String email);

    User findById(Long id);
}
