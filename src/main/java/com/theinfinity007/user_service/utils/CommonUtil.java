package com.theinfinity007.user_service.utils;

import com.theinfinity007.user_service.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.oauth2.jwt.Jwt;

import java.security.SecureRandom;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtil {

    private final static String emailClaim = "email";

    public static String getClientIPAddress(List<String> xForwardedFor, HttpServletRequest request) {

        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {

            String[] ipList = xForwardedFor.getFirst().split(",");
            for(String ip: ipList) {
                ip = ip.trim();
                if (!ip.isEmpty()) {
                    return ip;
                }
            }
        }

        return request.getRemoteAddr();
    }

    public static Map<String, Object> prepareTokenPayload(User user) {
        Map<String, Object> payload = new HashMap<>();
        payload.put(emailClaim, user.getEmail());

        return payload;
    }

    public static Long getUserIdFromToken(Jwt jwt){
        return Long.valueOf(jwt.getClaim("sub"));
    }

    public static String getEmailFromToken(Jwt jwt){
        return jwt.getClaim("email");
    }

    public static String getCurrentTimestamp(){
        return Instant.now().truncatedTo(ChronoUnit.MILLIS).toString();
    }

    public static String generateRandomString(int length) {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[length];
        random.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    public static boolean checkExpiry(Date incomingValue, int gracePeriodInMs) {
        return System.currentTimeMillis() > (incomingValue.getTime() + gracePeriodInMs);
    }

    public static String getInterpolatedString(String str, Map<String, String> map) {

        Pattern pattern = Pattern.compile("\\{(\\w+)\\}");
        Matcher matcher = pattern.matcher(str);

        return matcher.replaceAll((match) -> {
            String key = match.group(1);
            return map.getOrDefault(key, match.group());
        });
    }
}
