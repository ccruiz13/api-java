package com.personalsoft.btgfund.com.api_java.domain.constanst;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ValidationMessage {

    EMAIL_REQUIRED("El correo es obligatorio."),
    EMAIL_INVALID("El correo electrónico no tiene un formato válido."),
    PASSWORD_REQUIRED("La contraseña es obligatoria."),
    REGEX_VALID_EMAIL("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"),
    USER_NOT_FOUND_BY_EMAIL_MESSAGE ("No se encontró un usuario con el correo electrónico proporcionado.");


    private final String message;
}
