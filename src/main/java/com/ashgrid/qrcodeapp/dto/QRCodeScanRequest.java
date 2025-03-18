package com.ashgrid.qrcodeapp.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QRCodeScanRequest {
    private String qrData;
}
