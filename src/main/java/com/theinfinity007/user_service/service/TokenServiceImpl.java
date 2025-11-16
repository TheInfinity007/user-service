package com.theinfinity007.user_service.service;

import com.theinfinity007.user_service.dao.TokenDAO;
import com.theinfinity007.user_service.dto.JWTResponse;
import com.theinfinity007.user_service.entity.Token;
import com.theinfinity007.user_service.entity.User;
import com.theinfinity007.user_service.enums.TokenType;
import com.theinfinity007.user_service.utils.CryptoUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.Instant;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final JWTTokenProviderImpl tokenProvider;
    private final TokenDAO tokenDAO;
    private final int REFRESH_TOKEN_LENGTH = 45;

    @Override
    public JWTResponse generateToken(Map<String, Object> payload, User user, String ip) {

        JWTResponse result = tokenProvider.signJWT(payload, user.getId().toString());

        String refreshToken = CryptoUtil.getRandomString(REFRESH_TOKEN_LENGTH);
        result.setRefreshToken(refreshToken);

        Token token = new Token();
        token.setToken(refreshToken);
        token.setType(TokenType.REFRESH_TOKEN);
        token.setUser(user);
        token.setLastUsedAt(Date.from(Instant.now()));
        token.setAccessCount(0);
        token.setIp(ip);

        this.create(token);
        return result;
    }

    @Transactional
    @Override
    public Token create(Token token) {
        return tokenDAO.create(token);
    }


}
