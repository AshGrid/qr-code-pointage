package com.ashgrid.qrcodeapp.controllers;


import com.ashgrid.qrcodeapp.entities.User;
import com.ashgrid.qrcodeapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PutMapping("/users/{userId}/role")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<String> updateUserRole(
            @PathVariable Long userId,
            @RequestParam User.Role newRole) {
        userService.changeUserRole(userId, newRole);
        return ResponseEntity.ok("User role updated successfully");
    }


}
