package com.capstone2024.fap_system.services;

import com.capstone2024.fap_system.dto.SemesterDTO;
import com.capstone2024.fap_system.repository.SemesterRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SemesterService {

    private final SemesterRepository semesterRepository;

    public SemesterService(SemesterRepository semesterRepository) {
        this.semesterRepository = semesterRepository;
    }

    public List<SemesterDTO> getAllSemesters() {
        return semesterRepository.findAll().stream()
                .map(semester -> SemesterDTO.builder()
                        .name(semester.getName())
                        .build())
                .collect(Collectors.toList());
    }
}

