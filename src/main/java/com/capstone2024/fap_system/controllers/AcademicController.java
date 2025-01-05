package com.capstone2024.fap_system.controllers;

import com.capstone2024.fap_system.dto.DepartmentDTO;
import com.capstone2024.fap_system.dto.SemesterDTO;
import com.capstone2024.fap_system.services.DepartmentService;
import com.capstone2024.fap_system.services.SemesterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/academic")
public class AcademicController {

    private final DepartmentService departmentService;
    private final SemesterService semesterService;

    public AcademicController(DepartmentService departmentService, SemesterService semesterService) {
        this.departmentService = departmentService;
        this.semesterService = semesterService;
    }

    @GetMapping("/departments")
    public ResponseEntity<List<DepartmentDTO>> getAllDepartments() {
        return ResponseEntity.ok(departmentService.getAllDepartments());
    }

    @GetMapping("/semesters")
    public ResponseEntity<List<SemesterDTO>> getAllSemesters() {
        return ResponseEntity.ok(semesterService.getAllSemesters());
    }
}

