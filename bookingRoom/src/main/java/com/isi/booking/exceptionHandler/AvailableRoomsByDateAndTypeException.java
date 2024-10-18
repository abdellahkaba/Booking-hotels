package com.isi.booking.exceptionHandler;

public class AvailableRoomsByDateAndTypeException extends RuntimeException{

    public AvailableRoomsByDateAndTypeException(String message){
        super(message);
    }

}
