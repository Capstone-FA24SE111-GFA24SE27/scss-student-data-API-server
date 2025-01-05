package com.capstone2024.fap_system.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "subject")
public class Subject extends BaseEntity {

    @Column(name = "code", nullable = false)
    private String code;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", nullable = true)
    private Department department;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "credit", nullable = true)
    private int credit;

    @Enumerated(EnumType.STRING)
    @Column(name = "subject_type", nullable = false)
    private SubjectType subjectType;

    public enum SubjectType {
        IN_PROGRAM, // Môn trong khung chương trình
        OUT_OF_PROGRAM, // Môn ngoài khung chương trình
        ELECTIVE
    }
}
