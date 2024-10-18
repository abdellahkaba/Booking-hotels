package com.isi.booking.exception;

public class RoomNotAvailableForSelectDateRange extends RuntimeException {
    public RoomNotAvailableForSelectDateRange(String message){
        super(message);
    }

}
