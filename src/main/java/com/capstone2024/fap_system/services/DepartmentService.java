package com.capstone2024.fap_system.services;

import com.capstone2024.fap_system.dto.DepartmentDTO;
import com.capstone2024.fap_system.dto.MajorDTO;
import com.capstone2024.fap_system.dto.SpecializationDTO;
import com.capstone2024.fap_system.repository.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public List<DepartmentDTO> getAllDepartments() {
        return departmentRepository.findAll().stream()
                .map(department -> DepartmentDTO.builder()
                        .name(department.getName())
                        .code(department.getCode())
                        .majors(department.getMajors().stream()
                                .map(major -> MajorDTO.builder()
                                        .name(major.getName())
                                        .code(major.getCode())
                                        .specializations(major.getSpecializations().stream()
                                                .map(specialization -> SpecializationDTO.builder()
                                                        .name(specialization.getName())
                                                        .code(specialization.getCode())
                                                        .build())
                                                .collect(Collectors.toList()))
                                        .build())
                                .collect(Collectors.toList()))
                        .build())
                .collect(Collectors.toList());
    }
}
