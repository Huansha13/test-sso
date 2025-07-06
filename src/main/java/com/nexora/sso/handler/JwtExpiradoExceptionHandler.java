package com.nexora.sso.handler;

import com.nexora.config.enums.MensajeEnum;
import com.nexora.config.enums.SummaryEnum;
import com.nexora.config.to.ResponseDto;
import com.nexora.config.utils.Constantes;
import com.nexora.config.utils.ToolUtils;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class JwtExpiradoExceptionHandler {

    private JwtExpiradoExceptionHandler() {
        throw new IllegalStateException("JwtExpiradoExceptionHandler class");
    }

    public static void handleExpiredJwtException(
            HttpServletResponse response
    ) throws IOException {
        ResponseDto<Void> responseDto = new ResponseDto<>();
        responseDto.setSummary(SummaryEnum.SESION_EXPIRADO.getValue());
        responseDto.setMessage(MensajeEnum.SESION_EXPIRADO.getValue());
        responseDto.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        String json = ToolUtils.json(responseDto);

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(Constantes.CONTENT_TYPE_JSON_UTF8);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }
}
