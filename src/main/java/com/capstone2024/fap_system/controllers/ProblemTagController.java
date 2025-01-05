package com.capstone2024.fap_system.controllers;

import com.capstone2024.fap_system.dto.DemandProblemTagDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/problem-tags")
public class ProblemTagController {
    public static List<DemandProblemTagDTO> studentProblemTag = new ArrayList<>();

    @GetMapping("/{studentCode}")
    public ResponseEntity<List<DemandProblemTagDTO>> getStudentProblemTags(@PathVariable String studentCode) {
        return new ResponseEntity<>(studentProblemTag.stream().filter(tag -> tag.getStudentCode().equals(studentCode)).collect(Collectors.toList()), HttpStatus.OK);
    }
}

