package com.isi.booking.exceptionHandler;

public class PhoneConflictException extends RuntimeException{

    public PhoneConflictException(String message){
        super(message);
    }
}
