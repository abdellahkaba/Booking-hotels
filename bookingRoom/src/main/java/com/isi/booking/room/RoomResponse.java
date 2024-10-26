package com.isi.booking.room;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomResponse {
    private Integer id;
    private String roomType;
    private String roomPrice;
    private String roomPhotoUrl;
    private String roomDescription;
}
