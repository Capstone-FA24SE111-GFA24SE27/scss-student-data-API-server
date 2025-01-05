package com.capstone2024.fap_system.repository;

import com.capstone2024.fap_system.entities.Major;
import com.capstone2024.fap_system.entities.SubjectForMajor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectForMajorRepository extends JpaRepository<SubjectForMajor, Long> {
    List<SubjectForMajor> findByMajor(Major major);
    // Thêm các phương thức tìm kiếm nếu cần thiết
}
