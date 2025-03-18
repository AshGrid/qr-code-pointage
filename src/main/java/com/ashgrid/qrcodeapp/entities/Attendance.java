package com.ashgrid.qrcodeapp.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
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

    @Column(nullable = false)
    private String qrData;
    @Column(nullable = false)
    private LocalDateTime scannedAt;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user;
}
