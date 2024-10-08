package com.isi.booking.booking;

import org.springframework.stereotype.Component;

@Component
public class BookingMapper {
    public BookingResponse fromBooking(Booking booking) {
        return BookingResponse.builder()
                .id(booking.getId())
                .checkInDate(booking.getCheckInDate())
                .checkOutDate(booking.getCheckOutDate())
                .bookingConfirmationCode(booking.getBookingConfirmationCode())
                .roomType(booking.getRoom().getRoomType())
                .userName(booking.getUser().getName())
                .build();
    }
}
