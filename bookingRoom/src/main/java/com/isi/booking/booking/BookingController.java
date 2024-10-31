package com.isi.booking.booking;


import com.isi.booking.common.PageResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("booking")
@Tag(name = "Booking")
public class BookingController {

    private final BookingService service;
    @PostMapping("/to-book")
    public ResponseEntity<String> saveBooking(
            @RequestBody @Valid BookingRequest request,
            Authentication connecteUser
    ){
        return ResponseEntity.ok(service.saveBooking(request,connecteUser)) ;
    }

    @GetMapping("/all")
    public ResponseEntity<PageResponse<BookingResponse>> getAllBookings(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "2", required = false) int size
    ){
        return ResponseEntity.ok(service.getAllBookings(page,size));
    }

    @GetMapping("/get-by-confirmation-code/{confirmation-code}")
    public ResponseEntity<BookingResponse> findBookingByConfirmationCode(
            @PathVariable("confirmation-code") String confirmationCode
    ){
        return ResponseEntity.ok(service.findBookingByConfirmationCode(confirmationCode));
    }

    @DeleteMapping("cancel/{booking-id}")
    public ResponseEntity<Void> cancelBooking(
            @PathVariable("booking-id") Integer bookingId
    ){
        service.cancelBooking(bookingId);
        return ResponseEntity.accepted().build();
    }
}
