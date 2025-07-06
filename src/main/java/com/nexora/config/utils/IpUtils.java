package com.nexora.config.utils;

import jakarta.servlet.http.HttpServletRequest;

public class IpUtils {

    private IpUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static String getClientIp(HttpServletRequest request) {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader != null && !xfHeader.isBlank()) {
            // Si hay más de una IP, tomamos la primera (la del cliente real)
            return xfHeader.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }
}
