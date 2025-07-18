package com.personalsoft.btgfund.com.api_java.application.documentation;

public class OpenApiConstants {

    public static final String EMAIL_DESCRIPTION = "Correo electrónico del usuario";
    public static final String EMAIL_EXAMPLE = "usuario@example.com";

    public static final String PASSWORD_DESCRIPTION = "Clave del usuario, entre 6 y 13 caracteres";
    public static final String PASSWORD_EXAMPLE = "clave123";

    public static final String TAG_USERS_NAME = "Usuarios";
    public static final String TAG_DESCRIPTION = "Api que genera token";

    public static final String ROLE_DESCRIPTION = "Rol asignado al usuario. Valores permitidos: ADMIN, USER, AUDITOR.";
    public static final String ROLE_EXAMPLE = "ADMIN";

    public static final String LOGIN_SUMMARY = "Iniciar Sesion";
    public static final String LOGIN_DESCRIPTION = "Autenticacion del sistema para consumo de apis";
    public static final String LOGIN_REQUEST = "Objeto de login";

    public static final String HTTP_201 = "201";
    public static final String RESPONSE_201 = "Usuario creado exitosamente.";

    public static final String HTTP_200 = "200";
    public static final String RESPONSE_200 = "Inicio de sesión exitoso.";

    public static final String HTTP_400 = "400";
    public static final String RESPONSE_400 = "Solicitud inválida. Faltan datos requeridos o el formato es incorrecto.";

    public static final String HTTP_401 = "401";
    public static final String RESPONSE_401 = "No autorizado. Las credenciales proporcionadas no son válidas.";

    public static final String HTTP_500 = "500";
    public static final String RESPONSE_500 = "Error interno del servidor. Ocurrió un problema inesperado al procesar la solicitud.";

    public static final String CREATE_USER_SUMMARY = "Crear nuevo usuario";
    public static final String CREATE_USER_DESCRIPTION = "Permite crear un nuevo usuario con los datos proporcionados.";
    public static final String CREATE_USER_REQUEST = "Datos requeridos para registrar un nuevo usuario.";

    public static final String DECODE_SUMMARY = "Valida el token";
    public static final String DECODE_DESCRIPTION = "Decodifica el token para validar que esta correcto";



    private OpenApiConstants() {
    }
}
