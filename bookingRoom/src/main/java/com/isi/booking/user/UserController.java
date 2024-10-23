package com.isi.booking.user;


import com.isi.booking.common.PageResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("user")
@Tag(name = "User")
public class UserController {

    private final UserService service;
    @GetMapping("all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<PageResponse<ResponseUser>> getAllUsers(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "2", required = false) int size
    ){
        return ResponseEntity.ok(service.getAllUsers(page, size));

    }
    @GetMapping("/get-user-by-id/{user-id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseUser> getUserById(
            @PathVariable("user-id") Integer userId
    ){
        return ResponseEntity.ok(service.getUserById(userId));
    }


    @DeleteMapping("delete/{user-id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteUser(
            @PathVariable("user-id") Integer userId
    ){
        service.deleteUser(userId);
        return ResponseEntity.accepted().build();
    }
    @GetMapping("/get-logged-in-profile-info")
    public ResponseEntity<ResponseUser> getLoggedUserProfile(){
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        String email = authentication.getName();
        ResponseUser responseUser = service.getInfo(email);
        return ResponseEntity.ok(responseUser);
    }
    @PostMapping("/assign-admin-role/{user-id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<User> assignAdminRole(
            @PathVariable("user-id") Integer userId
    ){
        User user = service.assignAdminRole(userId);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }
    @PostMapping("/assign-manager-role/{user-id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<User> assignManagerRole(
            @PathVariable("user-id") Integer userId
    ){
        User user = service.assignManagerRole(userId);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }
}
