package com.ashgrid.qrcodeapp.services;

import com.ashgrid.qrcodeapp.entities.QrData;
import com.ashgrid.qrcodeapp.repositories.QrDataRepository;
import com.ashgrid.qrcodeapp.utils.EncryptionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QrDataService {

    private final QrDataRepository qrDataRepository;

    public void addQrData(final QrData qrData) {
        try {
            // Encrypt the QR data before saving
            String encryptedData = EncryptionUtil.encrypt(qrData.getQrCodeData());

            // Create a new entity with encrypted data
            QrData encryptedQrData = new QrData();
            encryptedQrData.setQrCodeData(encryptedData);
            encryptedQrData.setCreatedAt(qrData.getCreatedAt());

            // Save to database
            qrDataRepository.save(encryptedQrData);
        } catch (Exception e) {
            throw new RuntimeException("Error encrypting QR Code Data", e);
        }
    }

    public List<QrData> getQrCodes() {
        return qrDataRepository.findAll().stream().map(qrData -> {
            try {
                // Decrypt the QR code data before returning
                String decryptedData = EncryptionUtil.decrypt(qrData.getQrCodeData());

                // Create a new QrData object with decrypted data
                QrData decryptedQrData = new QrData();
                decryptedQrData.setId(qrData.getId()); // Preserve ID
                decryptedQrData.setQrCodeData(decryptedData);
                decryptedQrData.setCreatedAt(qrData.getCreatedAt());

                return decryptedQrData;
            } catch (Exception e) {
                throw new RuntimeException("Error decrypting QR Code Data", e);
            }
        }).collect(Collectors.toList());
    }


    public QrData findByQrCodeData(String qrCodeData) {
        return qrDataRepository.findByQrCodeData(qrCodeData);
    }

    public QrData findById(Long id) {
        return qrDataRepository.findById(id).orElse(null);
    }

    public QrData getQrDataByTodaysDate() {
        return qrDataRepository.findQrDataByToday();
    }
}
