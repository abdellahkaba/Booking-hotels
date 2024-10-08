package com.isi.booking.exceptionHandler;

public class FoundByConfirmationCodeException extends RuntimeException{
    public FoundByConfirmationCodeException(String message){
        super(message);
    }
}
