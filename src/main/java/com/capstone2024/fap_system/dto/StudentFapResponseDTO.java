package com.capstone2024.fap_system.dto;

import com.capstone2024.fap_system.entities.enums.Gender;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentFapResponseDTO {
    private String studentCode;
    private String batch;
    private String email;
    private String fullName;
    private String majorName;
    private int currentTerm;
    private String departmentName;
    private String specializationName;
    private Gender gender;
    private String avatarLink;
    private String phoneNumber;
    private String address;
    private Long dateOfBirth;
}
