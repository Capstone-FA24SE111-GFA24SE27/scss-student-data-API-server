package com.capstone2024.fap_system.controllers;

import com.capstone2024.fap_system.dto.StudyDTO;
import com.capstone2024.fap_system.services.StudyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/studies")
public class StudyController {

    private final StudyService studyService;

    public StudyController(StudyService studyService) {
        this.studyService = studyService;
    }

    @GetMapping("/{studentCode}")
    public ResponseEntity<List<StudyDTO>> getStudiesByStudentCode(@PathVariable String studentCode) {
        List<StudyDTO> studies = studyService.getStudiesByStudentCode(studentCode);
        return ResponseEntity.ok(studies);
    }
}
