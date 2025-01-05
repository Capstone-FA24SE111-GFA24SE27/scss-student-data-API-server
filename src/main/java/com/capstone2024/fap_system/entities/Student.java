package com.capstone2024.fap_system.entities;

import com.capstone2024.fap_system.entities.enums.Gender;
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
@Table(name = "student")
public class Student extends BaseEntity {

    @Column(name = "student_code", nullable = false)
    private String studentCode;

    @Column(name = "batch", nullable = false)
    private String batch;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "major_id", nullable = true)
    private Major major;

    @Column(name = "current_term", nullable = false)
    private int currentTerm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", nullable = true)
    private Department department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "specialization_id", nullable = true)
    private Specialization specialization;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "avatar_link", nullable = true)
    private String avatarLink;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "address")
    private String address;

    @Column(name = "date_of_birth")
    private Long dateOfBirth;
}
