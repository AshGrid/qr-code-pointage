package com.ashgrid.qrcodeapp.services;

import com.ashgrid.qrcodeapp.entities.Attendance;
import com.ashgrid.qrcodeapp.entities.User;
import com.ashgrid.qrcodeapp.repositories.AttendanceRepository;
import lombok.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;

    public void recordAttendance(User user, String qrData) {
        Attendance attendance = new Attendance();
        attendance.setUser(user);
        attendance.setQrData(qrData);
        attendance.setScannedAt(LocalDateTime.now());

        attendanceRepository.save(attendance);
    }
}
