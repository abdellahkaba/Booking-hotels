package com.isi.booking.room;


import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("room")
@Tag(name = "Room")
public class RoomController {

    private final RoomService service ;
    @PostMapping("add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Integer> addRoom(
            @ModelAttribute @Valid RoomRequest request
    ){
        return ResponseEntity.ok(service.addRoom(request));
    }
    @GetMapping("/all")
    public ResponseEntity<List<RoomResponse>> getAllRooms(){
        return ResponseEntity.ok(service.getAllRooms());
    }
    @GetMapping("/types")
    public List<String> getAllRoomTypes(

    ){
        return service.getAllRoomTypes();
    }

    @GetMapping("/{room-id}")
    public ResponseEntity<RoomResponse> getRoomById(
            @PathVariable("room-id") Integer roomId
    ){
        return ResponseEntity.ok(service.getRoomById(roomId));
    }
    @PutMapping("/update/{room-id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> updateRoom(
            @PathVariable("room-id") Integer id,
            @ModelAttribute @Valid RoomUpdateRequest request
    ){
        request = new RoomUpdateRequest(
                    id,
                request.roomType(),
                request.roomPrice(),
                request.roomPhotoUrl(),
                request.roomDescription()
        );
        service.updateRoom(request);

        return ResponseEntity.accepted().build();
    }

    @DeleteMapping("/delete/{room-id}")
    public ResponseEntity<Void> deleteRoom(
            @PathVariable("room-id") Integer roomId
    ){
        service.deleteRoom(roomId);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/list-available-rooms")
    public ResponseEntity<List<RoomResponse>> getAvailableRooms(){
        return ResponseEntity.ok(service.getAvailableRooms());
    }

    /*@PostMapping(value = "/photo/{room-id}", consumes = "multipart/form-data")
    public ResponseEntity<?> uploadRoomPhoto(
            @PathVariable("room-id") Integer roomId,
            @Parameter()
            @RequestPart("file")MultipartFile file
            ){
        service.uploadRoomPhoto(file, roomId);
        return ResponseEntity.accepted().build();
    }*/

}
