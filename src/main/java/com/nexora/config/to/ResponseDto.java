package com.nexora.config.to;

import com.nexora.config.enums.EstadoRespuestaEnum;
import com.nexora.config.enums.MensajeEnum;
import com.nexora.config.enums.SummaryEnum;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.authentication.BadCredentialsException;

import java.nio.file.AccessDeniedException;
import java.security.SignatureException;

@Getter
@Setter
public class ResponseDto<T> {
    private int status;
    private String message;
    private String summary; // Resumen
    private String severity; // Severidad
    private T value;

    public ResponseDto() {
        super();
        this.status = HttpStatus.OK.value();
        this.severity = "success";
    }

    public ResponseDto(String summary) {
        super();
        this.status = HttpStatus.OK.value();
        this.severity = "success";
        this.summary = summary;
    }

    public void setStatus(int status) {
        String estado = String.valueOf(status);
        if (EstadoRespuestaEnum.OK.getValue().equals(estado)) {
            this.severity = "success";
        } else if (EstadoRespuestaEnum.NOT_FOUND.getValue().equals(estado) ||
                HttpStatus.NO_CONTENT.value() == status) {
            this.severity = "warn";
        } else {
            this.severity = "error";
        }
        this.status = status;
    }

    public void setException(Exception ex) {
        ProblemDetail problemDetail;

        if (ex instanceof BadCredentialsException) {
            problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, MensajeEnum.ERROR_LOGIN.getValue());
            problemDetail.setProperty("error_reason", SummaryEnum.ERROR_LOGIN.getValue());
        } else if (ex instanceof AccessDeniedException) {
            problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, MensajeEnum.NO_AUTORIZADO.getValue());
            problemDetail.setProperty("error_reason", SummaryEnum.NO_AUTORIZADO.getValue());
        } else if (ex instanceof SignatureException) {
            problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, MensajeEnum.ERROR_FIRMA_JWT.getValue());
            problemDetail.setProperty("error_reason", SummaryEnum.ERROR_FIRMA_JWT.getValue());
        } else if (ex instanceof ExpiredJwtException) {
            problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, MensajeEnum.TOKEN_CADUCADO.getValue());
            problemDetail.setProperty("error_reason", SummaryEnum.TOKEN_CADUCADO.getValue());
        } else {
            problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, MensajeEnum.ERROR_SERVER.getValue() +  ": " + ex.getMessage());
            problemDetail.setProperty("error_reason", SummaryEnum.ERROR_SERVER.getValue());
        }

        status = problemDetail.getStatus();
        summary = (String) problemDetail.getProperties().get("error_reason");
        message = problemDetail.getDetail();
        severity = "error";
    }
}
