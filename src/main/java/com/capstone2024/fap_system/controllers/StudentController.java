package com.capstone2024.fap_system.controllers;

import com.capstone2024.fap_system.dto.*;
import com.capstone2024.fap_system.entities.Student;
import com.capstone2024.fap_system.repository.StudentRepository;
import com.capstone2024.fap_system.services.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentRepository studentRepository;
    private final AttendanceService attendanceService;

    @GetMapping
    public List<StudentFapResponseDTO> getAllStudents() {
        return studentRepository.findAll().stream().map(student -> {
            StudentFapResponseDTO dto = new StudentFapResponseDTO();
            dto.setStudentCode(student.getStudentCode());
            dto.setBatch(student.getBatch());
            dto.setEmail(student.getEmail());
            dto.setFullName(student.getFullName());
            dto.setMajorName(student.getMajor() != null ? student.getMajor().getName() : null);
            dto.setCurrentTerm(student.getCurrentTerm());
            dto.setDepartmentName(student.getDepartment() != null ? student.getDepartment().getName() : null);
            dto.setSpecializationName(student.getSpecialization() != null ? student.getSpecialization().getName() : null);
            dto.setGender(student.getGender());
            return dto;
        }).collect(Collectors.toList());
    }

    @GetMapping("/{studentCode}")
    public ResponseEntity<StudentFapResponseDTO> getAllStudents(@PathVariable String studentCode) {
        Optional<Student> studentOptional = studentRepository.findByStudentCode(studentCode);
        if(studentOptional.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        Student student = studentOptional.get();

        StudentFapResponseDTO dto = new StudentFapResponseDTO();
        dto.setStudentCode(student.getStudentCode());
        dto.setBatch(student.getBatch());
        dto.setEmail(student.getEmail());
        dto.setFullName(student.getFullName());
        dto.setMajorName(student.getMajor() != null ? student.getMajor().getName() : null);
        dto.setCurrentTerm(student.getCurrentTerm());
        dto.setDepartmentName(student.getDepartment() != null ? student.getDepartment().getName() : null);
        dto.setSpecializationName(student.getSpecialization() != null ? student.getSpecialization().getName() : null);
        dto.setGender(student.getGender());

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/{studentCode}/semester/{semesterName}")
    public ResponseEntity<List<AttendanceDTO>> getAttendanceByStudentCodeAndSemesterName(
            @PathVariable String studentCode,
            @PathVariable String semesterName) {

        List<AttendanceDTO> attendances = attendanceService.getAttendanceByStudentCodeAndSemesterName(studentCode, semesterName);
        return ResponseEntity.ok(attendances);
    }

    @GetMapping("/study/all-semester/{studentCode}")
    public ResponseEntity<List<AttendanceDTO>> getAllAttendanceByStudentCode(
            @PathVariable String studentCode) {

        List<AttendanceDTO> attendances = attendanceService.getAllAttendanceByStudentCode(studentCode);
        return ResponseEntity.ok(attendances);
    }

    @GetMapping("/{studentCode}/attendance/{attendanceId}")
    public ResponseEntity<List<AttendanceDetailDTO>> getAttendanceDetailsByStudentCodeAndAttendanceId(
            @PathVariable String studentCode,
            @PathVariable Long attendanceId) {

        List<AttendanceDetailDTO> details = attendanceService.getAttendanceDetailsByStudentCodeAndAttendanceId(studentCode, attendanceId);
        return ResponseEntity.ok(details);
    }

    @GetMapping("/attendance/{semesterName}/count/filter")
    public ResponseEntity<List<String>> getStudentAttendanceFilterCount(
            @PathVariable String semesterName,
            @RequestParam(required = false, defaultValue = "0") Long absenceSlotFrom,
            @RequestParam(required = false, defaultValue = "99999") Long absenceSlotTo,
            @RequestParam(required = false, defaultValue = "0") Long subjectcountFrom,
            @RequestParam(required = false) Long subjectcountTo
    ) {

        List<String> details = attendanceService.getStudentAttendanceFilterCount(
                semesterName,
                AttendanceFilterCountDTO.builder()
                        .absenceSlotFrom(absenceSlotFrom)
                        .absenceSlotTo(absenceSlotTo)
                        .subjectcountFrom(subjectcountFrom)
                        .subjectcountTo(subjectcountTo)
                        .build()
        );
        return ResponseEntity.ok(details);
    }

    @GetMapping("/attendance/{semesterName}/percentage/filter")
    public ResponseEntity<List<String>> getStudentAttendanceFilterPercent(
            @PathVariable String semesterName,
            @RequestParam(required = false, defaultValue = "0") Double absenceSlotFrom,
            @RequestParam(required = false, defaultValue = "99999") Double absenceSlotTo,
            @RequestParam(required = false, defaultValue = "0") Long subjectcountFrom,
            @RequestParam(required = false) Long subjectcountTo
    ) {

        List<String> details = attendanceService.getStudentAttendanceFilterPercentage(
                semesterName,
                AttendanceFilterPercentageDTO.builder()
                        .absenceSlotFrom(absenceSlotFrom)
                        .absenceSlotTo(absenceSlotTo)
                        .subjectcountFrom(subjectcountFrom)
                        .subjectcountTo(subjectcountTo)
                        .build()
        );
        return ResponseEntity.ok(details);
    }

    @GetMapping("/gpa/{semesterName}/filter")
    public ResponseEntity<List<String>> getStudentGPAFilter(
            @PathVariable String semesterName,
            @RequestParam(required = false, defaultValue = "0") Double from,
            @RequestParam(required = false, defaultValue = "99999") Double to
    ) {

        List<String> details = attendanceService.getStudentGPAFilter(
                semesterName,
                GPAFilterDTO.builder()
                        .from(from)
                        .to(to)
                        .build()
        );
        return ResponseEntity.ok(details);
    }
}

