package com.ashgrid.qrcodeapp.repositories;

import com.ashgrid.qrcodeapp.entities.Attendance;
import com.ashgrid.qrcodeapp.entities.QrData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QrDataRepository extends JpaRepository<QrData, Long> {

    QrData findByQrCodeData(String qrCodeData);

    @Query("SELECT q FROM QrData q WHERE FUNCTION('DATE', q.createdAt) = FUNCTION('CURRENT_DATE')")
    QrData findQrDataByToday();
}
