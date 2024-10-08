package com.isi.booking.booking;

import com.isi.booking.exceptionHandler.BusinessErrorCodes;
import com.isi.booking.exceptionHandler.FoundByConfirmationCodeException;
import com.isi.booking.exceptionHandler.InvalidBookingDateException;
import com.isi.booking.exceptionHandler.RoomNotAvailableForSelectDateRange;
import com.isi.booking.room.RoomRepository;
import com.isi.booking.user.UserRepository;
import com.isi.booking.utils.Utils;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final BookingRepository repository;
    private final BookingMapper mapper;

    private boolean roomIsAvailable(Booking booking, List<Booking> existingBookings){
        return existingBookings
                .stream()
                .noneMatch(exist -> booking.getCheckInDate().equals(exist.getCheckInDate()) ||
                        booking.getCheckOutDate().isBefore(exist.getCheckOutDate()) ||
                        (booking.getCheckInDate().isAfter(exist.getCheckInDate()) && booking.getCheckInDate().isBefore(exist.getCheckOutDate())) ||
                        (booking.getCheckInDate().isBefore(exist.getCheckInDate()) && booking.getCheckOutDate().equals(exist.getCheckOutDate())) ||
                        (booking.getCheckInDate().isBefore(exist.getCheckInDate()) && booking.getCheckOutDate().isAfter(exist.getCheckOutDate())) ||
                        (booking.getCheckInDate().equals(exist.getCheckOutDate()) && booking.getCheckOutDate().equals(exist.getCheckInDate())) ||
                        (booking.getCheckInDate().equals(exist.getCheckOutDate()) && booking.getCheckOutDate().equals(booking.getCheckInDate()))
                );
    }

    public Integer saveBooking(Integer roomId, Integer userId, Booking request) {

        if (request.getCheckOutDate().isBefore(request.getCheckInDate())){
            throw new InvalidBookingDateException(BusinessErrorCodes.INVALIDATE_CHECKINDATE_AND_CHECKOUTDATE.getDescription());
        }
        var room = roomRepository.findById(roomId)
                .orElseThrow(() -> new EntityNotFoundException(BusinessErrorCodes.ENTITY_NOT_FOUND.getDescription() + " ID :: " + roomId));
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(BusinessErrorCodes.ENTITY_NOT_FOUND.getDescription() + " ID :: " + userId));

        List<Booking> existingBookings = room.getBookings();

        if (!roomIsAvailable(request,existingBookings)){
            throw new RoomNotAvailableForSelectDateRange(BusinessErrorCodes.ROOM_NOT_AVAILABLE_FOR_SELECT_DATE_RANGE.getDescription());
        }
        request.setRoom(room);
        request.setUser(user);
        String bookingConfirmationCode = Utils.generateRandomConfirmationCode(10);
        request.setBookingConfirmationCode(bookingConfirmationCode);
        return repository.save(request).getId();

    }
    public List<BookingResponse> getAllBookings() {
        return repository.findAll()
                .stream()
                .map(mapper::fromBooking)
                .collect(Collectors.toList());
    }
    public BookingResponse findBookingByConfirmationCode(String confirmationCode) {
        return repository.findByBookingConfirmationCode(confirmationCode)
                .map(mapper::fromBooking)
                .orElseThrow(() -> new FoundByConfirmationCodeException(BusinessErrorCodes.NOT_FOUND_CONFIRMATION_CODE.getDescription() + " code :: " + confirmationCode));
    }
}
