package com.ashgrid.qrcodeapp.services;

import com.ashgrid.qrcodeapp.dto.AttendanceDTO;
import com.ashgrid.qrcodeapp.dto.QrDataDTO;
import com.ashgrid.qrcodeapp.dto.UserDTO;
import com.ashgrid.qrcodeapp.entities.Attendance;
import com.ashgrid.qrcodeapp.entities.QrData;
import com.ashgrid.qrcodeapp.entities.User;
import com.ashgrid.qrcodeapp.repositories.AttendanceRepository;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    public void recordAttendance(User user, QrData qrData) {
        Attendance attendance = new Attendance();
        attendance.setUser(user);
        attendance.setQrData(qrData);  // ✅ Now storing QrData entity
        attendance.setScannedAt(LocalDateTime.now());

        attendanceRepository.save(attendance);
    }

    public List<Attendance> getAttendancesByUser(User user) {
        return  attendanceRepository.findAllByUserId(user.getId());
    }

    public List<AttendanceDTO> getAllAttendances() {
        List<Attendance> attendances = attendanceRepository.findAll();

        return attendances.stream()
                .map(this::convertToDTO) // ✅ Use a helper method
                .collect(Collectors.toList());
    }

    private AttendanceDTO convertToDTO(Attendance attendance) {
        return new AttendanceDTO(
                attendance.getId(),
                attendance.getScannedAt(),
                new UserDTO(
                        attendance.getUser().getId(),
                        attendance.getUser().getFullName(),
                        attendance.getUser().getEmail(),
                        attendance.getUser().getPhoneId()
                ),
                new QrDataDTO(
                        attendance.getQrData().getId(),
                        attendance.getQrData().getQrCodeData(),
                        attendance.getQrData().getCreatedAt()
                                )
        );
    }



}

