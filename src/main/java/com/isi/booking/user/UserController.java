package com.isi.booking.user;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("user")
@Tag(name = "User")
public class UserController {

    private final UserService service;
    @GetMapping("/all")
    public ResponseEntity<List<ResponseUser>> getAllUsers(){
        return ResponseEntity.ok(service.getAllUsers());

    }
}
