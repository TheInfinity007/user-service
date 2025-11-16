package com.theinfinity007.user_service.utils;

import org.springframework.security.crypto.keygen.Base64StringKeyGenerator;

public class CryptoUtil {
    public static String getRandomString(int length) {
        return new Base64StringKeyGenerator(length).generateKey();
    }
}
