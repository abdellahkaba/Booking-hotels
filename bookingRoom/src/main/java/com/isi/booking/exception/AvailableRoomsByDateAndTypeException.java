package com.isi.booking.exception;

public class AvailableRoomsByDateAndTypeException extends RuntimeException{

    public AvailableRoomsByDateAndTypeException(String message){
        super(message);
    }

}
