package com.nexora.sso.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexora.config.enums.MensajeEnum;
import com.nexora.config.enums.SummaryEnum;
import com.nexora.config.to.ResponseDto;
import com.nexora.config.utils.Constantes;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       org.springframework.security.access.AccessDeniedException accessDeniedException
    ) throws IOException {
        ResponseDto<Void> responseDto = new ResponseDto<>();
        responseDto.setSummary(SummaryEnum.ACCESO_DENEGADO.getValue());
        responseDto.setMessage(MensajeEnum.ACCESO_DENEGADO.getValue());
        responseDto.setStatus(HttpServletResponse.SC_FORBIDDEN);

        String json = new ObjectMapper().writeValueAsString(responseDto);

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType(Constantes.CONTENT_TYPE_JSON_UTF8);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }
}
