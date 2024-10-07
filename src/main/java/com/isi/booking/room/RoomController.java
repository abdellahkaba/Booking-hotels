package com.isi.booking.room;


import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("room")
@Tag(name = "Room")
public class RoomController {

    private final RoomService service ;

    @PostMapping("add")
    public ResponseEntity<Integer> addRoom(
            @ModelAttribute @Valid RoomRequest request
    ){
        return ResponseEntity.ok(service.addRoom(request));
    }

    @PostMapping(value = "/photo/{room-id}", consumes = "multipart/form-data")
    public ResponseEntity<?> uploadRoomPhoto(
            @PathVariable("room-id") Integer roomId,
            @Parameter()
            @RequestPart("file")MultipartFile file
            ){
        service.uploadRoomPhoto(file, roomId);
        return ResponseEntity.accepted().build();
    }

}
