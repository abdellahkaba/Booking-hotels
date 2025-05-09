package com.isi.booking.handler;


import com.isi.booking.exception.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashSet;
import java.util.Set;

import static com.isi.booking.handler.BusinessErrorCodes.BAD_CREDENTIALS;
import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ExceptionResponse> handleException() {
        return ResponseEntity
                .status(UNAUTHORIZED)
                .body(
                        ExceptionResponse.builder()
                                .businessErrorCode(BAD_CREDENTIALS.getCode())
                                .businessErrorDescription(BAD_CREDENTIALS.getDescription())
                                .error("Le login et/ou le mot de passe sont incorrects")
                                .build()
                );
    }

    @ExceptionHandler(EmailConflictException.class)
    public ResponseEntity<ExceptionResponse> handleException(EmailConflictException exp) {
        return ResponseEntity
                .status(BusinessErrorCodes.DUPLICATE_EMAIL.getHttpStatus())
                .body(
                        ExceptionResponse.builder()
                                .businessErrorCode(BusinessErrorCodes.DUPLICATE_EMAIL.getCode())
                                .businessErrorDescription(BusinessErrorCodes.DUPLICATE_EMAIL.getDescription())
                                .error(exp.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(PhoneConflictException.class)
    public ResponseEntity<ExceptionResponse> handleException(PhoneConflictException exp) {
        return ResponseEntity
                .status(BusinessErrorCodes.DUPLICATE_PHONE.getHttpStatus())
                .body(
                        ExceptionResponse.builder()
                                .businessErrorCode(BusinessErrorCodes.DUPLICATE_PHONE.getCode())
                                .businessErrorDescription(BusinessErrorCodes.DUPLICATE_PHONE.getDescription())
                                .error(exp.getMessage())
                                .build()
                );
    }


    @ExceptionHandler(OperationNotPermittedException.class)
    public ResponseEntity<ExceptionResponse> handleException(OperationNotPermittedException exp) {
        return ResponseEntity
                .status(BAD_REQUEST)
                .body(
                        ExceptionResponse.builder()
                                .error(exp.getMessage())
                                .build()
                );
    }

    //Gestion de l'exception d'access non autoriser
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ExceptionResponse> handleAccessDeniedException(AccessDeniedException ex) {
        return ResponseEntity
                .status(BusinessErrorCodes.UNAUTHORIZED_ACCESS.getHttpStatus())
                .body(
                        ExceptionResponse.builder()
                                .businessErrorCode(BusinessErrorCodes.UNAUTHORIZED_ACCESS.getCode())
                                .businessErrorDescription(BusinessErrorCodes.UNAUTHORIZED_ACCESS.getDescription())
                                .error(ex.getMessage())
                                .build()
                );
    }

    // Gestion de l'exception lorsque l'entité n'est pas trouvée
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleEntityNotFoundException(EntityNotFoundException exp) {
        return ResponseEntity
                .status(BusinessErrorCodes.ENTITY_NOT_FOUND.getHttpStatus())
                .body(
                        ExceptionResponse.builder()
                                .businessErrorCode(BusinessErrorCodes.ENTITY_NOT_FOUND.getCode())
                                .businessErrorDescription(BusinessErrorCodes.ENTITY_NOT_FOUND.getDescription())
                                .error(exp.getMessage())
                                .build()
                );
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exp) {
        Set<String> errors = new HashSet<>();
        exp.getBindingResult().getAllErrors()
                .forEach(error -> {
                   // var fieldName = ((FieldError) error).getField();
                    var errorMessage = error.getDefaultMessage();
                    errors.add(errorMessage);
                });

        return ResponseEntity
                .status(BAD_REQUEST)
                .body(
                        ExceptionResponse.builder()
                                .validationErrors(errors)
                                .build()
                );
    }

    @ExceptionHandler(InvalidBookingDateException.class)
    public ResponseEntity<ExceptionResponse> handleException(InvalidBookingDateException exp) {
        return ResponseEntity
                .status(BusinessErrorCodes.INVALIDATE_CHECKINDATE_AND_CHECKOUTDATE.getHttpStatus())
                .body(
                        ExceptionResponse.builder()
                                .businessErrorCode(BusinessErrorCodes.INVALIDATE_CHECKINDATE_AND_CHECKOUTDATE.getCode())
                                .businessErrorDescription(BusinessErrorCodes.INVALIDATE_CHECKINDATE_AND_CHECKOUTDATE.getDescription())
                                .error(exp.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(RoomNotAvailableForSelectDateRange.class)
    public ResponseEntity<ExceptionResponse> handleException(RoomNotAvailableForSelectDateRange exp) {
        return ResponseEntity
                .status(BusinessErrorCodes.ROOM_NOT_AVAILABLE_FOR_SELECT_DATE_RANGE.getHttpStatus())
                .body(
                        ExceptionResponse.builder()
                                .businessErrorCode(BusinessErrorCodes.ROOM_NOT_AVAILABLE_FOR_SELECT_DATE_RANGE.getCode())
                                .businessErrorDescription(BusinessErrorCodes.ROOM_NOT_AVAILABLE_FOR_SELECT_DATE_RANGE.getDescription())
                                .error(exp.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(FoundByConfirmationCodeException.class)
    public ResponseEntity<ExceptionResponse> handleException(FoundByConfirmationCodeException exp) {
        return ResponseEntity
                .status(BusinessErrorCodes.NOT_FOUND_CONFIRMATION_CODE.getHttpStatus())
                .body(
                        ExceptionResponse.builder()
                                .businessErrorCode(BusinessErrorCodes.NOT_FOUND_CONFIRMATION_CODE.getCode())
                                .businessErrorDescription(BusinessErrorCodes.NOT_FOUND_CONFIRMATION_CODE.getDescription())
                                .error(exp.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(AvailableRoomsByDateAndTypeException.class)
    public ResponseEntity<ExceptionResponse> handleException(AvailableRoomsByDateAndTypeException exp) {
        return ResponseEntity
                .status(BusinessErrorCodes.INVALID_AVAILABLE_ROOMS_BY_DATE_AND_TYPE.getHttpStatus())
                .body(
                        ExceptionResponse.builder()
                                .businessErrorCode(BusinessErrorCodes.INVALID_AVAILABLE_ROOMS_BY_DATE_AND_TYPE.getCode())
                                .businessErrorDescription(BusinessErrorCodes.INVALID_AVAILABLE_ROOMS_BY_DATE_AND_TYPE.getDescription())
                                .error(exp.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(RoleNameException.class)
    public ResponseEntity<ExceptionResponse> handleException(RoleNameException exp) {
        return ResponseEntity
                .status(BusinessErrorCodes.ROLE_NAME_NOT_EXIST.getHttpStatus())
                .body(
                        ExceptionResponse.builder()
                                .businessErrorCode(BusinessErrorCodes.ROLE_NAME_NOT_EXIST.getCode())
                                .businessErrorDescription(BusinessErrorCodes.ROLE_NAME_NOT_EXIST.getDescription())
                                .error(exp.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(Exception exp) {
        exp.printStackTrace();
        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .body(
                        ExceptionResponse.builder()
                                .businessErrorDescription("Erreur interne, veuillez contacter l'administrateur")
                                .error(exp.getMessage())
                                .build()
                );
    }
}
