package com.theinfinity007.user_service.dao;

import com.theinfinity007.user_service.entity.Token;

public interface TokenDAO {
    Token create(Token token);

    Token findByToken(String token);

    Token save(Token token);

    void deleteByUserId(Long userId);
}
