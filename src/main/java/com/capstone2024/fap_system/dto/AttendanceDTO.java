package com.capstone2024.fap_system.dto;

import com.capstone2024.fap_system.entities.enums.StudyStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
public class AttendanceDTO {
    private Long id;
    private LocalDate startDate;
    private int totalSlot;
    private String studentCode;
    private String subjectCode;
    private String subjectName;
    private String semesterName;
    private BigDecimal grade;
    private StudyStatus status;
    private List<AttendanceDetailDTO> detais;
}
