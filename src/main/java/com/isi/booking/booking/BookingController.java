package com.isi.booking.booking;


import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("booking")
@Tag(name = "Booking")
public class BookingController {

    private final BookingService service;
    @PostMapping("/to-book/{room-id}/{user-id}")
    public ResponseEntity<Integer> saveBooking(
            @PathVariable("room-id") Integer roomId,
            @PathVariable("user-id") Integer userId,
            @RequestBody @Valid Booking request
    ){
        return ResponseEntity.ok(service.saveBooking(roomId,userId,request)) ;
    }
}
