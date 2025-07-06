package com.nexora.config.spring;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Nexora Team",
                        email = "contacto@nexora.com",
                        url = "https://nexora.com"
                ),
                description = "Documentación oficial de la API SSO de Nexora.",
                title = "Nexora SSO API",
                version = "1.0"
        ),
        servers = {
                @Server(
                        description = "Ambiente de Desarrollo",
                        url = "http://localhost:8080/sso"
                ),
                @Server(
                        description = "Ambiente de Producción",
                        url = "https://edu.control.com/sso"
                )
        }
)

@SecurityScheme(
        name = "bearerAuth",
        description = "JWT Auth description",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}
