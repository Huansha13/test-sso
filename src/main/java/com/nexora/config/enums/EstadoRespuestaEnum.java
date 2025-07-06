package com.nexora.config.enums;

public enum EstadoRespuestaEnum {
    OK("200"),
    NOT_FOUND("404"),
    NOT_CREATE("300"),
    BAD_REQUEST("400"),
    INTERNAL_SERVER_ERROR("500");

    private final String value;

    EstadoRespuestaEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
