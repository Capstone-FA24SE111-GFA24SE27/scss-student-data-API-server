package com.capstone2024.fap_system.repository;

import com.capstone2024.fap_system.entities.Student;
import com.capstone2024.fap_system.entities.Study;
import com.capstone2024.fap_system.entities.enums.StudyStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudyRepository extends JpaRepository<Study, Long> {
    List<Study> findByStudent_StudentCodeOrderByTermAsc(String studentCode);

    List<Study> findByStudentAndStatusNot(Student student, StudyStatus studyStatus);
}
