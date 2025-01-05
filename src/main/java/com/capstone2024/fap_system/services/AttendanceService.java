package com.capstone2024.fap_system.services;

import com.capstone2024.fap_system.dto.*;
import com.capstone2024.fap_system.entities.Attendance;
import com.capstone2024.fap_system.entities.AttendanceDetail;
import com.capstone2024.fap_system.entities.Student;
import com.capstone2024.fap_system.repository.AttendanceDetailRepository;
import com.capstone2024.fap_system.repository.AttendanceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final AttendanceDetailRepository attendanceDetailRepository;

    public AttendanceService(AttendanceRepository attendanceRepository, AttendanceDetailRepository attendanceDetailRepository) {
        this.attendanceRepository = attendanceRepository;
        this.attendanceDetailRepository = attendanceDetailRepository;
    }

    public List<AttendanceDTO> getAttendanceByStudentCodeAndSemesterName(String studentCode, String semesterName) {
        List<Attendance> attendances = attendanceRepository.findByStudent_StudentCodeAndSemester_Name(studentCode, semesterName);
        return attendances.stream()
                .map(attendance -> AttendanceDTO.builder()
                        .id(attendance.getId())
                        .startDate(attendance.getStartDate())
                        .grade(attendance.getGrade())
                        .totalSlot(attendance.getTotalSlot())
                        .studentCode(attendance.getStudent().getStudentCode())
                        .subjectName(attendance.getSubject().getName()) // giả sử Subject có thuộc tính name
                        .semesterName(attendance.getSemester().getName())
                        .detais(attendance.getAttendanceDetails().stream().map(this::toDetailDTO).collect(Collectors.toList()))
                        .build())
                .collect(Collectors.toList());
    }

    public List<AttendanceDetailDTO> getAttendanceDetailsByStudentCodeAndAttendanceId(String studentCode, Long attendanceId) {
        List<AttendanceDetail> attendanceDetails = attendanceDetailRepository.findByAttendance_IdAndAttendance_Student_StudentCode(attendanceId, studentCode);
        return attendanceDetails.stream()
                .map(this::toDetailDTO)
                .collect(Collectors.toList());
    }

    private AttendanceDetailDTO toDetailDTO(AttendanceDetail detail) {
        return AttendanceDetailDTO.builder()
                .date(detail.getDate())
                .slot(detail.getSlot())
                .room(detail.getRoom())
                .lecturer(detail.getLecturer())
                .groupName(detail.getGroupName())
                .status(detail.getStatus())
                .lecturerComment(detail.getLecturerComment())
                .build();
    }

    public List<String> getStudentAttendanceFilterCount(String semesterName, AttendanceFilterCountDTO filterDTO) {
        List<Student> students = attendanceDetailRepository.findStudentsWithAbsenceCountRange(
               semesterName,
                filterDTO.getAbsenceSlotFrom(),
                filterDTO.getAbsenceSlotTo(),
                filterDTO.getSubjectcountFrom()
//                ,
//                filterDTO.getSubjectcountTo()
        );

        return students.stream().map(Student::getStudentCode).collect(Collectors.toList());
    }

    public List<String> getStudentAttendanceFilterPercentage(String semesterName, AttendanceFilterPercentageDTO filterDTO) {
        List<Student> students = attendanceDetailRepository.findStudentsWithAbsencePercentageRange(
                semesterName,
                filterDTO.getAbsenceSlotFrom(),
                filterDTO.getAbsenceSlotTo(),
                filterDTO.getSubjectcountFrom()
//                ,
//                filterDTO.getSubjectcountTo()
        );

        return students.stream().map(Student::getStudentCode).collect(Collectors.toList());
    }

    public List<String> getStudentGPAFilter(String semesterName, GPAFilterDTO filterDTO) {
        List<Student> students = attendanceDetailRepository.findStudentsWithGPA(
                semesterName,
                filterDTO.getFrom(),
                filterDTO.getTo()
        );

        return students.stream().map(Student::getStudentCode).collect(Collectors.toList());
    }

    public List<AttendanceDTO> getAllAttendanceByStudentCode(String studentCode) {
        List<Attendance> attendances = attendanceRepository.findByStudent_StudentCode(studentCode);
        return attendances.stream()
                .map(attendance -> AttendanceDTO.builder()
                        .id(attendance.getId())
                        .startDate(attendance.getStartDate())
                        .grade(attendance.getGrade())
                        .totalSlot(attendance.getTotalSlot())
                        .studentCode(attendance.getStudent().getStudentCode())
                        .subjectName(attendance.getSubject().getName()) // giả sử Subject có thuộc tính name
                        .subjectCode(attendance.getSubject().getCode())
                        .status(attendance.getStatus())
                        .semesterName(attendance.getSemester().getName())
                        .detais(attendance.getAttendanceDetails().stream().map(this::toDetailDTO).collect(Collectors.toList()))
                        .build())
                .collect(Collectors.toList());
    }
}

