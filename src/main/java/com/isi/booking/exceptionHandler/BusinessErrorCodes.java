package com.isi.booking.exceptionHandler;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_IMPLEMENTED;
@Getter
public enum BusinessErrorCodes {
    NO_CODE(0, NOT_IMPLEMENTED, "Aucun code"),
    INCORRECT_CURRENT_PASSWORD(300, BAD_REQUEST, "Le mot de passe actuel est incorrect"),
    NEW_PASSWORD_DOES_NOT_MATCH(301, BAD_REQUEST, "Le nouveau mot de passe ne correspond pas"),
    BAD_CREDENTIALS(304, FORBIDDEN, "Le login et/ou le mot de passe sont incorrects"),
    DUPLICATE_EMAIL(409, HttpStatus.CONFLICT, "Email déjà utilisé"),
    DUPLICATE_PHONE(409, HttpStatus.CONFLICT, "Le numero de téléphone existe déjà"),
    DUPLICATE_NAME(409, HttpStatus.CONFLICT, "Ce nom existe déjà"),
    ENTITY_NOT_FOUND(404, HttpStatus.NOT_FOUND, "Entité non trouvée"),
    UNAUTHORIZED_ACCESS(403, HttpStatus.FORBIDDEN, "Accès non autorisé");

    ;
    private final int code;
    private final String description;
    private final HttpStatus httpStatus;
    BusinessErrorCodes(int code, HttpStatus status, String description) {
        this.code = code;
        this.description = description;
        this.httpStatus = status;
    }
}
