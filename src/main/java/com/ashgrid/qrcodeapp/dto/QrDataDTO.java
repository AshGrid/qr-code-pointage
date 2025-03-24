package com.ashgrid.qrcodeapp.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QrDataDTO {
    private Long id;
    private String qrCodeData;
    private LocalDateTime createdAt;
}
