package com.isi.booking.room;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface RoomRepository extends JpaRepository<Room,Integer> {
    @Query("select distinct r.roomType from Room r")
    List<String> findDistinctRoomTypes();
    @Query("select r from Room r where r.id not in (select b.room.id from Booking b)")
    Page<Room> getAvailableRooms(Pageable pageable);
    @Query("SELECT r FROM Room r WHERE r.roomType LIKE %:roomType% AND r.id NOT IN (SELECT bk.room.id FROM Booking bk WHERE" +
            "(bk.checkInDate <= :checkOutDate) AND (bk.checkOutDate >= :checkInDate))")
    Page<Room> findAvailableRoomsByDatesAndTypes(Pageable pageable, LocalDate checkInDate, LocalDate checkOutDate, String roomType);
    @Query("SELECT room FROM Room room")
    Page<Room> findAllDisplayRooms(Pageable pageable);
}
