package com.isi.booking.exceptionHandler;

public class RoomNotAvailableForSelectDateRange extends RuntimeException {
    public RoomNotAvailableForSelectDateRange(String message){
        super(message);
    }

}
