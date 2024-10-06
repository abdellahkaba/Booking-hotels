package com.isi.booking.room;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


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
}
