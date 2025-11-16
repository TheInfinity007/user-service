package com.theinfinity007.user_service.utils;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public class CommonUtil {
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
}
