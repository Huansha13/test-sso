package com.nexora.config.enums;

import lombok.Getter;

@Getter
public enum MensajeEnum {
    SIN_REGISTROS("No hay información disponible en este momento."),
    SIN_REGISTROS_DRIVE("* Actualmente no hay archivos subidos en Drive. Recuerda que puedes subir archivos en cualquier momento."),
    CAMPO_INCORRECTO("Se han ingresado datos incorrectos"),
    USUARIO_NO_ENCONTRADO("Usuario no encontrado"),
    LOGIN_OK ("Acceso confirmado. ¡Bienvenido!"),
    ERROR_LOGIN ("Credenciales inválidas. Por favor, verifica tu nombre de usuario y contraseña."),
    NO_AUTORIZADO ("Acceso denegado. No estás autorizado para realizar esta acción."),
    ERROR_FIRMA_JWT ("Acceso denegado. La firma del token JWT no es válida."),
    TOKEN_CADUCADO ("Acceso denegado, El token JWT ha expirado. Por favor, vuelve a iniciar sesión."),
    ERROR_SERVER("Ocurrió un error inesperado en el servidor. Por favor, inténtalo de nuevo más tarde."),
    REGISTRO_OK("Su solicitud ha sido registrada con éxito"),
    SOLICITUD_WARN("Lo sentimos, no se pudo procesar su solicitud."),
    SOLICITUD_OK("Su solicitud se ha procesado correctamente"),
    SESION_EXPIRADO("Lo siento, el sesión ha expirado. Por favor, vuelve a iniciar sesión."),
    PARAMETRO_ACTUALIZADO_EXITOSAMENTE("Parametro actualizado exitosamente"),
    PARAMETRO_ELIMINADO_EXITOSAMENTE("Parametro eliminado exitosamente"),
    REGISTRO_DUPLICADO("El código de solicitud de ya existe"),
    ACCESO_DENEGADO("No cuentas con los permisos necesarios para acceder a este recurso."),
    CAMPO_USUARIO_INCORRECTO("El nombre de usuario debe tener entre 3 y 10 caracteres."),
    CAMPO_PASSWORD_INCORRECTO("La contraseña debe tener entre 8 y 16 caracteres."),
    SUBJECT_NULL("El subject no debe ser null");

    private final String value;
    MensajeEnum(String value) {
        this.value = value;
    }

}
