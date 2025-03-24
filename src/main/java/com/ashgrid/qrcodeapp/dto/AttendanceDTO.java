package com.ashgrid.qrcodeapp.dto;

import com.ashgrid.qrcodeapp.entities.QrData;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceDTO {
    private Long id;
    private LocalDateTime scannedAt;
    private UserDTO user;
    private QrDataDTO qrData;


}
