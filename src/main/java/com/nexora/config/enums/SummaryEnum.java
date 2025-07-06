package com.nexora.config.enums;

import lombok.Getter;

@Getter
public enum SummaryEnum {
    GET_MENU("Obtener Menus"),
    POST_LOGIN("Inicio Sesión"),
    ERROR_LOGIN ("Error de autenticación"),
    NO_AUTORIZADO ("¡No autorizado!"),
    ERROR_FIRMA_JWT ("Firma JWT no válida"),
    TOKEN_CADUCADO ("¡Token JWT caducado!"),
    REGISTRO_USER ("Registro nuevo usuario"),
    REGISTRO_ANEXO ("Registro nuevo anexo"),
    ERROR_SERVER("Error desconocido"),
    GET_USUARIOS("Obtener Usuarios"),
    GET_ANEXO("Obtener Anexos"),
    ELIMINAR_USUARIO("Eliminar Usuario"),
    VERIFICAR_SESION("Verificando Sesión"),
    GET_CONTROL_POST("Obtener control de poste"),
    GET_CONTROL_POST_RESUMEN("Obtener resumen control de poste"),
    POST_OBRA_COMUNICADO("Comunicado de la Obra"),
    GET_COMUNICADOS("Obtener Comunicados"),
    FACTURAR_OBRA("Facturación Obra"),
    FOLDER_OBRA("Creando Folder obra"),
    DESCARGANDO_ARCHIVO_DRIVE("Descargando archivos del drive"),
    LINEA_TIEMPO_OBRA("Linea tiempo Obra"),
    POST_GUIA_SOLICITUD("Creando Guia de Solicitud"),
    GET_GUIA_SOLICITUD("Obtener Guia de Solicitud"),
    GET_STOCK_POSTE("Obtener Stock Poste"),
    GET_INVENTARIO_POSTE("Obtener Inventario Poste"),
    DELETE_PARAMETRO("Eliminar Parametro"),
    GUARDAR_PARAMETRO("Guardar Parametro"),
    GET_CONDUCTOR("Obtener Conductores"),
    SESION_EXPIRADO("Sesión expirada"),
    ACCESO_DENEGADO("Acceso denegado");


    private final String value;
    SummaryEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
