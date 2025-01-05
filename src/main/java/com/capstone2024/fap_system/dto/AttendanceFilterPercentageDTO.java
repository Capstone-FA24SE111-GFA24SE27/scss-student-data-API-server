package com.capstone2024.fap_system.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AttendanceFilterPercentageDTO {
    private Double absenceSlotFrom;
    private Double absenceSlotTo;
    private Long subjectcountFrom;
    private Long subjectcountTo;
}
