package com.ashgrid.qrcodeapp.repositories;

import com.ashgrid.qrcodeapp.entities.Attendance;

import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {


    List<Attendance> findAllByUserId(Long userId);

}
