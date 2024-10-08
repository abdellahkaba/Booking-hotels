package com.isi.booking.exceptionHandler;

public class InvalidBookingDateException extends RuntimeException {
    public InvalidBookingDateException(String message){
        super(message);
    }
}
