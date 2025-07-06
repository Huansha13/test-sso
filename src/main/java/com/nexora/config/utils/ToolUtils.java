package com.nexora.config.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ToolUtils {
    private ToolUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static String json(Object value) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            return  "";
        }
    }
}
