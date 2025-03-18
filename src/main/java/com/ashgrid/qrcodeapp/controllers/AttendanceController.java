package com.ashgrid.qrcodeapp.controllers;

import com.ashgrid.qrcodeapp.dto.QRCodeScanRequest;
import com.ashgrid.qrcodeapp.entities.User;
import com.ashgrid.qrcodeapp.repositories.UserRepository;
import com.ashgrid.qrcodeapp.services.AttendanceService;
import com.ashgrid.qrcodeapp.utils.JwtUtil;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/attendance")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;
    private final UserRepository userRepository;

    private final JwtUtil jwtUtil;


    @PostMapping("/scan")
    public ResponseEntity<String> scanQRCode(
            @RequestBody QRCodeScanRequest qrCodeScanRequest,
            @RequestHeader("Authorization") String authHeader) {

        // Extract the token from "Bearer <token>"
        String token = authHeader.substring(7); // Remove "Bearer " prefix

        // Extract email from JWT
        String email = jwtUtil.extractEmail(token); // Implement extractEmail() in JwtService

        // Fetch user from database using email
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Record attendance
        attendanceService.recordAttendance(user, qrCodeScanRequest.getQrData());

        return ResponseEntity.ok("Attendance recorded successfully.");
    }
}

