package com.theinfinity007.user_service.service;

import com.theinfinity007.user_service.entity.User;

public interface UserService {
    User create(User user);

    User update(User user);

    User findByEmail(String email);

    User findById(Long id);
}
