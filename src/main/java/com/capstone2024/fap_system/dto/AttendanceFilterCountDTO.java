package com.capstone2024.fap_system.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AttendanceFilterCountDTO {
    private Long absenceSlotFrom;
    private Long absenceSlotTo;
    private Long subjectcountFrom;
    private Long subjectcountTo;
}
