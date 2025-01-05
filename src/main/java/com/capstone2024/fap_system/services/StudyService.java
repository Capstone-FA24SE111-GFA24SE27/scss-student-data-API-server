package com.capstone2024.fap_system.services;

import com.capstone2024.fap_system.dto.StudyDTO;
import com.capstone2024.fap_system.entities.Study;
import com.capstone2024.fap_system.repository.StudyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudyService {

    private final StudyRepository studyRepository;

    public StudyService(StudyRepository studyRepository) {
        this.studyRepository = studyRepository;
    }

    public List<StudyDTO> getStudiesByStudentCode(String studentCode) {
        List<Study> studies = studyRepository.findByStudent_StudentCodeOrderByTermAsc(studentCode);
        return studies.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private StudyDTO convertToDTO(Study study) {
        return StudyDTO.builder()
                .subjectCode(study.getSubject().getCode())
                .subjectName(study.getSubject().getName())
                .term(study.getTerm())
                .grade(study.getGrade())
                .status(study.getStatus().name())
                .semester(study.getSemester() != null ? study.getSemester().getName() : "N/A")
                .build();
    }
}
