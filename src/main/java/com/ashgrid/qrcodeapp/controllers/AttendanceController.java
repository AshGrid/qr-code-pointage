package com.ashgrid.qrcodeapp.controllers;

import com.ashgrid.qrcodeapp.dto.AttendanceDTO;
import com.ashgrid.qrcodeapp.dto.QrDataDTO;
import com.ashgrid.qrcodeapp.entities.Attendance;
import com.ashgrid.qrcodeapp.entities.QrData;
import com.ashgrid.qrcodeapp.entities.User;
import com.ashgrid.qrcodeapp.repositories.UserRepository;
import com.ashgrid.qrcodeapp.services.AttendanceService;
import com.ashgrid.qrcodeapp.services.QrDataService;
import com.ashgrid.qrcodeapp.services.UserService;
import com.ashgrid.qrcodeapp.utils.EncryptionUtil;
import com.ashgrid.qrcodeapp.utils.JwtUtil;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/attendance")
@RequiredArgsConstructor
public class AttendanceController {


    @Autowired
    private final AttendanceService attendanceService;
    @Autowired
    private final UserService userService;
    @Autowired
    private QrDataService qrDataService;

    private final JwtUtil jwtUtil;


    @PostMapping("/scan")
    public ResponseEntity<String> scanQRCode(
            @RequestBody QrDataDTO qrCodeScanRequest,
            @RequestHeader("Authorization") String authHeader) {

        // Extract the token from "Bearer <token>"
        String token = authHeader.substring(7); // Remove "Bearer " prefix

        // Extract email from JWT
        String email = jwtUtil.extractEmail(token);

        // Fetch user from database using email
        User user = userService.findByEmail(email);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("User not found.");
        }

        try {
            // Get today's date (you can adjust the format to match your stored date format)


            // Fetch the QR data for today from the database
            QrData qrData = qrDataService.getQrDataByTodaysDate(); // Assuming you have a method to find QR data by date

            if (qrData == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("QR Code data for today not found.");
            }

            // Decrypt the QR data before comparing
            String decryptedQrData = EncryptionUtil.decrypt(qrData.getQrCodeData());  // Assuming getEncryptedData() returns the encrypted data

            // Compare the decrypted QR data with the QR data sent from frontend
            if (!decryptedQrData.equals(qrCodeScanRequest.getQrCodeData())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("QR Code data mismatch.");
            }

            // Now, record attendance with the matched QR data
            attendanceService.recordAttendance(user, qrData);  // Update to handle decrypted QR data

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error processing QR Code Data: " + e.getMessage());
        }

        return ResponseEntity.ok("Attendance recorded successfully.");
    }



//    @PostMapping("/scan")
//    public ResponseEntity<String> scanQRCode(
//            @RequestBody QrDataDTO qrCodeScanRequest,
//            @RequestHeader("Authorization") String authHeader) {
//
//        // Extract the token from "Bearer <token>"
//        String token = authHeader.substring(7); // Remove "Bearer " prefix
//
//        // Extract email from JWT
//        String email = jwtUtil.extractEmail(token);
//
//        // Fetch user from database using email
//        User user = userService.findByEmail(email);
//        if (user == null) {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN)
//                    .body("User not found.");
//        }
//
//        try {
//            // Encrypt the QR data before saving
//            String encryptedData = EncryptionUtil.encrypt(qrCodeScanRequest.getQrCodeData());
//
//            QrData qrData = qrDataService.findByQrCodeData(encryptedData);
//            attendanceService.recordAttendance(user, qrData);
//
//        } catch (Exception e) {
//            throw new RuntimeException("Error encrypting QR Code Data", e);
//        }
//
//
//        return ResponseEntity.ok("Attendance recorded successfully.");
//    }



    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @GetMapping("/getAll")
public ResponseEntity<List<AttendanceDTO>> getAttendance() {

    List<AttendanceDTO> attendances = attendanceService.getAllAttendances();

    return ResponseEntity.ok(attendances);
}

}

