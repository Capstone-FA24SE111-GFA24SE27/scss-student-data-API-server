package com.capstone2024.fap_system.repository;

import com.capstone2024.fap_system.entities.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> findByStudent_StudentCodeAndSemester_Name(String studentCode, String semesterName);

    List<Attendance> findByStudent_StudentCode(String studentCode);
}
