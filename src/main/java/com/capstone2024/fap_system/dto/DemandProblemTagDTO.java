package com.capstone2024.fap_system.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DemandProblemTagDTO {

    private Long id;              // ID của DemandProblemTag
    private String studentCode;        // ID của Student
    private String source;         // Nguồn gốc
    private String problemTagName;     // ID của ProblemTag
    private int number;            // Số lượng
    private String semesterName;       // ID của Semester
}

