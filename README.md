# Nexora SSO

Sistema de Single Sign-On (SSO) para la plataforma Nexora. Esta aplicación proporciona autenticación centralizada y gestión de sesiones para múltiples servicios.
## Documentación - swagger
- dev: http://localhost:8080/sso/swagger-ui/index.html#/
## Características

- Autenticación centralizada
- Gestión de sesiones
- Seguridad basada en JWT
- Integración con Spring Security
- Monitoreo y métricas con Spring Boot Actuator
- Persistencia de datos con JPA

## Instalación

1. Clonar el repositorio
2. Configurar las variables de entorno necesarias
3. Ejecutar la aplicación con Maven
4. Agregar como variables de entorno las siguientes:
    ```
   - SPRING_PROFILES_ACTIVE: Ambiente a utilizar (dev, qas o prd)
   - BD_URL: URL de la base de datos PostgreSQL
   - BD_USERNAME: Usuario de la base de datos
   - BD_PASSWORD: Contraseña de la base de datos
   - JWT_SECRET_DEV / JWT_SECRET_QAS / JWT_SECRET_PRD: Secreto JWT correspondiente al ambiente
    ```

```bash
mvn spring-boot:run
```

## Configuración

La configuración principal se encuentra en `application.yml`.

## Estructura del Proyecto

```
sso/
├── src/
│   ├── main/
│   │   ├── java/     # Código fuente
│   │   │   └── com.nexora.sso/
│   │   ├── resources/ # Recursos
│   │   │   ├── application.yml
│   │   │   └── static/
│   └── test/         # Pruebas
└── pom.xml          # Configuración de Maven
```

## Tecnologías Utilizadas

- Spring Boot 3.5.3
- Spring Security
- Spring Data JPA
- JWT (Java JWT)
- Maven
- Java 21