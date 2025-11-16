package com.theinfinity007.user_service.utils;

import org.bouncycastle.crypto.digests.SHA256Digest;
import org.springframework.security.crypto.keygen.Base64StringKeyGenerator;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class CryptoUtil {
    public static String getRandomString(int length) {
        return new Base64StringKeyGenerator(length).generateKey();
    }

    public static String getHash(String data){
        SHA256Digest digest = new SHA256Digest();
        byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);

        // Update digest with token bytes
        digest.update(dataBytes, 0, dataBytes.length);

        byte[] hash = new byte[digest.getDigestSize()];
        digest.doFinal(hash, 0);

        return Base64.getEncoder().encodeToString(hash);
    }
}
