package com.isi.booking.room;

import org.springframework.stereotype.Component;


@Component
public class RoomMapper {


    public Room toRoom(RoomRequest request) {
        if (request == null){
            return null;
        }
        return Room.builder()
                .id(request.id())
                .roomType(request.roomType())
                .roomPrice(request.roomPrice())
                .roomDescription(request.roomDescription())
                .build();
    }

    public RoomResponse fromRoom(Room room) {
       return RoomResponse.builder()
                .id(room.getId())
                .roomType(room.getRoomType())
                .roomPhotoUrl(room.getRoomPhotoUrl())
                .roomDescription(room.getRoomDescription())
                .build();
    }
}
