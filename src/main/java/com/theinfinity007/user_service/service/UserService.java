package com.theinfinity007.user_service.service;

import com.theinfinity007.user_service.entity.User;
import org.springframework.stereotype.Service;

public interface UserService {
    void create(User user);
}
