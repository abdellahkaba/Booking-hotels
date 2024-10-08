package com.isi.booking.booking;


import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("booking")
@Tag(name = "Booking")
public class BookingController {

    private final BookingService service;
    @PostMapping("/to-book")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ResponseEntity<String> saveBooking(
            @RequestBody @Valid BookingRequest request
    ){
        return ResponseEntity.ok(service.saveBooking(request)) ;
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<BookingResponse>> getAllBookings(){
        return ResponseEntity.ok(service.getAllBookings());
    }

    @GetMapping("/get-by-confirmation-code/{confirmation-code}")
    public ResponseEntity<BookingResponse> findBookingByConfirmationCode(
            @PathVariable("confirmation-code") String confirmationCode
    ){
        return ResponseEntity.ok(service.findBookingByConfirmationCode(confirmationCode));
    }

    @DeleteMapping("cancel/{booking-id}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ResponseEntity<Void> cancelBooking(
            @PathVariable("booking-id") Integer bookingId
    ){
        service.cancelBooking(bookingId);
        return ResponseEntity.accepted().build();
    }
}
