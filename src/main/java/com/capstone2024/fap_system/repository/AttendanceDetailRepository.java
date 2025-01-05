package com.capstone2024.fap_system.repository;

import com.capstone2024.fap_system.entities.AttendanceDetail;
import com.capstone2024.fap_system.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttendanceDetailRepository extends JpaRepository<AttendanceDetail, Long> {
    List<AttendanceDetail> findByAttendance_IdAndAttendance_Student_StudentCode(Long attendanceId, String studentCode);

    @Query("SELECT sto FROM Student sto " +
            "WHERE EXISTS (" +
            "    SELECT st1.studentCode " +
            "    FROM Attendance a1 " +
            "    JOIN a1.attendanceDetails ad1 " +
            "    JOIN a1.semester s1 " +
            "    JOIN a1.student st1 " +
            "    WHERE s1.name = :semesterIdForAttendance " +
            "      AND ad1.status = 'ABSENT' " +
            "      AND st1.studentCode = sto.studentCode " +
            "    GROUP BY st1.studentCode, a1.subject.id " +
            "    HAVING COUNT(ad1.id) BETWEEN :absenceSlotFrom AND :absenceSlotTo" +
            ") " +
            "AND (" +
            "    SELECT COUNT(DISTINCT a2.subject.id) " +
            "    FROM Attendance a2 " +
            "    JOIN a2.attendanceDetails ad2 " +
            "    JOIN a2.semester s2 " +
            "    JOIN a2.student st2 " +
            "    WHERE s2.name = :semesterIdForAttendance " +
            "      AND ad2.status = 'ABSENT' " +
            "      AND st2.studentCode = sto.studentCode " +
            "      AND (" +
            "          SELECT COUNT(*) " +
            "          FROM Attendance a3 " +
            "          JOIN a3.attendanceDetails ad3 " +
            "          JOIN a3.semester s3 " +
            "          JOIN a3.student st3 " +
            "          WHERE s3.name = :semesterIdForAttendance " +
            "            AND ad3.status = 'ABSENT' " +
            "            AND st3.studentCode = st2.studentCode " +
            "            AND a3.subject.id = a2.subject.id" +
            "      ) BETWEEN :absenceSlotFrom AND :absenceSlotTo" +
//            ") BETWEEN :subjectcountFrom AND :subjectcountTo")
            ") >= :subjectcountFrom")
    List<Student> findStudentsWithAbsenceCountRange(
            @Param("semesterIdForAttendance") String semesterIdForAttendance,
            @Param("absenceSlotFrom") Long absenceSlotFrom,
            @Param("absenceSlotTo") Long absenceSlotTo
            ,
            @Param("subjectcountFrom") Long subjectcountFrom
//            ,
//            @Param("subjectcountTo") Long subjectcountTo
    );

    @Query("SELECT sto FROM Student sto " +
            "WHERE EXISTS (" +
            "    SELECT st1.studentCode " +
            "    FROM Attendance a1 " +
            "    JOIN a1.attendanceDetails ad1 " +
            "    JOIN a1.semester s1 " +
            "    JOIN a1.student st1 " +
            "    WHERE s1.name = :semesterIdForAttendance " +
            "      AND ad1.status = 'ABSENT' " +
            "      AND st1.studentCode = sto.studentCode " +
            "    GROUP BY st1.studentCode, a1.subject.id " +
            "    HAVING (COUNT(ad1.id) * 100.0) / " +
            "           (SELECT COUNT(adTotal.id) " +
            "            FROM AttendanceDetail adTotal " +
            "            JOIN adTotal.attendance aTotal " +
            "            JOIN aTotal.student stTotal " +
            "            WHERE aTotal.subject.id = a1.subject.id " +
            "              AND stTotal.studentCode = st1.studentCode " +
            "              AND aTotal.semester.name = :semesterIdForAttendance) " +
            "           BETWEEN :absenceSlotFrom AND :absenceSlotTo" +
            ") " +
            "AND (" +
            "    SELECT COUNT(DISTINCT a2.subject.id) " +
            "    FROM Attendance a2 " +
            "    JOIN a2.attendanceDetails ad2 " +
            "    JOIN a2.semester s2 " +
            "    JOIN a2.student st2 " +
            "    WHERE s2.name = :semesterIdForAttendance " +
            "      AND ad2.status = 'ABSENT' " +
            "      AND st2.studentCode = sto.studentCode " +
            "      AND (" +
            "          SELECT (COUNT(ad3.id) * 100.0) / " +
            "                 (SELECT COUNT(adTotal.id) " +
            "                  FROM AttendanceDetail adTotal " +
            "                  JOIN adTotal.attendance aTotal " +
            "                  JOIN aTotal.student stTotal " +
            "                  WHERE aTotal.subject.id = a3.subject.id " +
            "                    AND stTotal.studentCode = st3.studentCode " +
            "                    AND aTotal.semester.name = :semesterIdForAttendance) " +
            "          FROM Attendance a3 " +
            "          JOIN a3.attendanceDetails ad3 " +
            "          JOIN a3.semester s3 " +
            "          JOIN a3.student st3 " +
            "          WHERE s3.name = :semesterIdForAttendance " +
            "            AND ad3.status = 'ABSENT' " +
            "            AND st3.studentCode = st2.studentCode " +
            "            AND a3.subject.id = a2.subject.id" +
            "      ) BETWEEN :absenceSlotFrom AND :absenceSlotTo" +
//            ") BETWEEN :subjectcountFrom AND :subjectcountTo")
            ") >= :subjectcountFrom")
    List<Student> findStudentsWithAbsencePercentageRange(
            @Param("semesterIdForAttendance") String semesterIdForAttendance,
            @Param("absenceSlotFrom") Double absenceSlotFrom,  // phần trăm tối thiểu
            @Param("absenceSlotTo") Double absenceSlotTo,      // phần trăm tối đa
            @Param("subjectcountFrom") Long subjectcountFrom
//            ,
//            @Param("subjectcountTo") Long subjectcountTo
    );

    @Query("SELECT sto FROM Student sto " +
            "WHERE (" +
            "    SELECT AVG(a1.grade) " +
            "    FROM Attendance a1 " +
            "    JOIN a1.semester s1 " +
            "    JOIN a1.student st1 " +
            "    WHERE s1.name = :semesterIdForGPA " +
            "      AND st1.studentCode = sto.studentCode " +
            ") BETWEEN :from AND :to")
    List<Student> findStudentsWithGPA(
            @Param("semesterIdForGPA") String semesterIdForGPA,
            @Param("from") Double from,  // phần trăm tối thiểu
            @Param("to") Double to
//            ,
//            @Param("subjectcountTo") Long subjectcountTo
    );
}
