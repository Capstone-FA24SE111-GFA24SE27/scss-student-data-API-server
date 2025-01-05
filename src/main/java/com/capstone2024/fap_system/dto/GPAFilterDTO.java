package com.capstone2024.fap_system.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GPAFilterDTO {
    private Double from;
    private Double to;
}
