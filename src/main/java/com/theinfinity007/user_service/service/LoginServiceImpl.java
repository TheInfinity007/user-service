package com.theinfinity007.user_service.service;

import com.theinfinity007.user_service.dto.JWTResponse;
import com.theinfinity007.user_service.entity.User;
import com.theinfinity007.user_service.exception.InvalidEmailorPasswordException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final OAuthTokenService oAuthTokenService;

    private final String emailClaim = "email";
    private final String idClaim = "id";

    @Override
    public JWTResponse login(String email, String password) {
        User user = userService.findByEmail(email);


        if (user == null || user.getPassword() == null) {
            throw new InvalidEmailorPasswordException();
        }

        boolean isValid = this.validatePassword(user.getPassword(), password);
        if (!isValid) {
            throw new InvalidEmailorPasswordException();
        }

        Map<String, Object> payload = new HashMap<>();
        payload.put(emailClaim, email);

        return oAuthTokenService.generateToken(payload, user.getId());
    }

    private boolean validatePassword(String hashedPassword, String password) {
        return passwordEncoder.matches(password, hashedPassword);
    }
}
