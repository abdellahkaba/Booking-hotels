package com.isi.booking.room;


import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

}
