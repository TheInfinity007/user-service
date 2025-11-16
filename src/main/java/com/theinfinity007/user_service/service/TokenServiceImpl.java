package com.theinfinity007.user_service.service;

import com.theinfinity007.user_service.dao.TokenDAO;
import com.theinfinity007.user_service.dto.JWTResponse;
import com.theinfinity007.user_service.entity.Token;
import com.theinfinity007.user_service.entity.User;
import com.theinfinity007.user_service.enums.TokenType;
import com.theinfinity007.user_service.exception.RefreshTokenNotFoundException;
import com.theinfinity007.user_service.utils.CommonUtil;
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

    private final JWTTokenProvider tokenProvider;
    private final TokenDAO tokenDAO;
    private final UserService userService;
    private final int REFRESH_TOKEN_LENGTH = 45;

    private String getRefreshTokenHash(String refreshToken) {
        return CryptoUtil.getHash(refreshToken);
    }

    @Override
    @Transactional
    public JWTResponse generateToken(Map<String, Object> payload, User user, String ip) {

        JWTResponse result = tokenProvider.signJWT(payload, user.getId().toString());

        String refreshToken = CryptoUtil.getRandomString(REFRESH_TOKEN_LENGTH);
        result.setRefreshToken(refreshToken);

        String refreshTokenHash = getRefreshTokenHash(refreshToken);

        Token token = new Token();
        token.setToken(refreshTokenHash);
        token.setType(TokenType.REFRESH_TOKEN);
        token.setUser(user);
        token.setAccessedAt(Date.from(Instant.now()));
        token.setAccessCount(0);
        token.setIp(ip);

        this.create(token);
        return result;
    }

    @Override
    @Transactional
    public Token create(Token token) {
        token =  tokenDAO.create(token);
        return token;
    }

    @Override
    @Transactional
    public JWTResponse refreshToken(String refreshToken, String ip) {
        Token token = this.findByRefreshToken(refreshToken);
        User user = userService.findById(token.getUser().getId());

        Map<String, Object> payload = CommonUtil.prepareTokenPayload(user);

        JWTResponse jwtResponse = tokenProvider.signJWT(payload, user.getId().toString());
        jwtResponse.setRefreshToken(refreshToken);

        token.setAccessCount(token.getAccessCount() + 1);
        token.setAccessedAt(Date.from(Instant.now()));
        token.setIp(ip);

        this.save(token);

        return jwtResponse;
    }

    private Token findByRefreshToken(String refreshToken) {
        String refreshTokenHash = getRefreshTokenHash(refreshToken);

        Token token = tokenDAO.findByToken(refreshTokenHash);
        if (token == null) {
            throw new RefreshTokenNotFoundException();
        }

        return token;
    }


    @Override
    public Token save(Token token) {
        return tokenDAO.save(token);
    }






}
