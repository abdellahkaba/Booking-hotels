package com.isi.booking.exception;

public class InvalidBookingDateException extends RuntimeException {
    public InvalidBookingDateException(String message){
        super(message);
    }
}
