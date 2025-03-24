package com.ashgrid.qrcodeapp.controllers;


import com.ashgrid.qrcodeapp.entities.QrData;
import com.ashgrid.qrcodeapp.services.QrDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/qr-data")
@RequiredArgsConstructor
public class QrDataController {

    @Autowired
    private final QrDataService qrDataService;

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PostMapping("add-Qr-code")
    public ResponseEntity<String> addQrCode(@RequestBody QrData qrCode) {
        qrDataService.addQrData(qrCode);
        return ResponseEntity.ok("qr code added successfully.");
    }

    @GetMapping("get-qr-codes")
    public ResponseEntity<List<QrData>> getQrCodes() {

        return ResponseEntity.ok(qrDataService.getQrCodes());
    }
}
