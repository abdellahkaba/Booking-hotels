package com.isi.booking.room;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Arrays;
import java.util.List;

public interface RoomRepository extends JpaRepository<Room,Integer> {
    @Query("select distinct r.roomType from Room r")
    List<String> findDistinctRoomTypes();


    @Query("select r from Room r where r.id not in (select b.room.id from Booking b)")
    List<Room> getAvailableRooms();
}
