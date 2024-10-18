package com.isi.booking.booking;

import org.springframework.data.jpa.repository.JpaRepository;

import java.nio.channels.FileChannel;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking,Integer> {
    Optional<Booking> findByBookingConfirmationCode(String confirmationCode);
}
