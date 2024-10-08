package com.isi.booking.booking;

import com.isi.booking.room.Room;
import com.isi.booking.user.User;
import org.springframework.stereotype.Component;

@Component
public class BookingMapper {

    public Booking toBooking(BookingRequest request){
        if (request == null){
            return null ;
        }
        return Booking.builder()
                .id(request.getId())
                .checkInDate(request.getCheckInDate())
                .checkOutDate(request.getCheckOutDate())
                .numOfAdults(request.getNumOfAdults())
                .numOfChildren(request.getNumOfChildren())
                .totalNumOfGuest(request.getTotalNumOfGuest())
                .room(Room.builder().id(request.getRoomId()).build())
                .user(User.builder().id(request.getUserId()).build())
                .build();
    }
        public BookingResponse fromBooking(Booking booking) {
        return BookingResponse.builder()
                .id(booking.getId())
                .checkInDate(booking.getCheckInDate())
                .checkOutDate(booking.getCheckOutDate())
                .numOfAdults(booking.getNumOfAdults())
                .numOfChildren(booking.getNumOfChildren())
                .totalNumOfGuest(booking.getTotalNumOfGuest())
                .bookingConfirmationCode(booking.getBookingConfirmationCode())
                .roomType(booking.getRoom().getRoomType())
                .userName(booking.getUser().getName())
                .build();
    }
}
