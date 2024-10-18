package com.isi.booking.room;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;
import java.math.BigDecimal;

public record RoomRequest(
        Integer id,
        @NotBlank(message = "Type de room requis")
        @NotEmpty(message = "Type de room requis")
        String roomType,
        @NotNull(message = "Le prix est requis")
        BigDecimal roomPrice,
        MultipartFile roomPhotoUrl,
        String roomDescription
) {
}
