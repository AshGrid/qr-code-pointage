package com.ashgrid.qrcodeapp.entities;


import com.fasterxml.jackson.annotation.*;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "qr_data_id", nullable = false)  // Every attendance links to ONE QrData
    private QrData qrData;

    @Column(nullable = false)
    private LocalDateTime scannedAt;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;
}
