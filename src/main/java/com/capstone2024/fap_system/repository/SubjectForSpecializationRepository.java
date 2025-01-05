package com.capstone2024.fap_system.repository;

import com.capstone2024.fap_system.entities.Specialization;
import com.capstone2024.fap_system.entities.SubjectForSpecialization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectForSpecializationRepository extends JpaRepository<SubjectForSpecialization, Long> {
    List<SubjectForSpecialization> findBySpecialization(Specialization specialization);
}
