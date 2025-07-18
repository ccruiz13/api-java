package com.personalsoft.btgfund.com.api_java.infraestructure.commons.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MessagesResponse {

    INVALID_CREDENTIALS_ERROR("Credenciales inválidas. Verifica tu correo y contraseña."),
    INVALID_TOKEN_ERROR("El token proporcionado no es válido."),
    LOGIN_SUCCESS_MESSAGE("Inicio de sesion exitoso"),
    CREATE_USER__SUCCESS_MESSAGE("Usuario creado exitosamente"),
    UNEXPECTED_LOGIN_ERROR_MESSAGE("Error inesperado durante el login."),
    USERNAME_NOT_FOUND_EXCEPTION("Usuario no encontrado con email: ");


    private final String message;

}
