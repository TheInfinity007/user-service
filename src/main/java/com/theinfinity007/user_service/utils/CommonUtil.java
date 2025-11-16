package com.theinfinity007.user_service.utils;

import com.theinfinity007.user_service.entity.User;
import jakarta.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
}
