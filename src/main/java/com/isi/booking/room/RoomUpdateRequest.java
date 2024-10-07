package com.isi.booking.room;

import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

public record RoomUpdateRequest(
        Integer id,
        String roomType,
        BigDecimal roomPrice,
        MultipartFile roomPhotoUrl,
        String roomDescription
) {
}
