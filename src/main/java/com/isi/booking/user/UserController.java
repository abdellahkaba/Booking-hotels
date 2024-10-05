package com.isi.booking.user;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("user")
@Tag(name = "User")
public class UserController {

    private final UserService service;
    @GetMapping
    public ResponseEntity<List<ResponseUser>> getAllUsers(){
        return ResponseEntity.ok(service.getAllUsers());

    }
    @GetMapping("/{user-id}")
    public ResponseEntity<ResponseUser> getUserById(
            @PathVariable("user-id") Integer userId
    ){
        return ResponseEntity.ok(service.getUserById(userId));
    }

    @DeleteMapping("delete/{user-id}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable("user-id") Integer userId
    ){
        service.deleteUser(userId);
        return ResponseEntity.accepted().build();
    }
}
