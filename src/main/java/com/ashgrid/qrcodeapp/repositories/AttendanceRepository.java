package com.ashgrid.qrcodeapp.repositories;

import com.ashgrid.qrcodeapp.entities.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

}
