package com.capstone2024.fap_system.data;

import com.capstone2024.fap_system.controllers.ProblemTagController;
import com.capstone2024.fap_system.dto.DemandProblemTagDTO;
import com.capstone2024.fap_system.entities.*;
import com.capstone2024.fap_system.entities.enums.AttendanceStatus;
import com.capstone2024.fap_system.entities.enums.Gender;
import com.capstone2024.fap_system.entities.enums.StudyStatus;
import com.capstone2024.fap_system.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DatabaseSeeder implements CommandLineRunner {

    private static final int BATCH_SIZE = 100;
    private final SubjectRepository subjectRepository;
    private final StudentRepository studentRepository;
    private final AttendanceRepository attendanceRepository;
    private final AttendanceDetailRepository attendanceDetailRepository;
    private final StudyRepository studyRepository;
    private final SemesterRepository semesterRepository;
    private final DepartmentRepository departmentRepository;
    private final MajorRepository majorRepository;
    private final SpecializationRepository specializationRepository;
    private final SubjectForMajorRepository subjectForMajorRepository;
    private final SubjectForSpecializationRepository subjectForSpecializationRepository;

    @Override
    public void run(String... args) throws Exception {
        if(studentRepository.count() == 0) {
            seedSemesters();
            seedStudentFieldData();

            seedElectiveSubjects();
            seedZeroTermSubjects();

            seedSubjectsForITDepartment();
            seedSubjectsForSoftwareEngineeringMajor();
            seedSubjectsForBridgeEngineerSpecialization();
            seedSubjectsForNodeJSSpecialization();

            seedStudents();
            seedStudies();
            seedAttendance();
        }
    }

    @Transactional
    private void seedSemesters() {
        List<String> semesterNames = List.of(
                "Spring2018", "Summer2018", "Fall2018",
                "Spring2019", "Summer2019", "Fall2019",
                "Spring2020", "Summer2020", "Fall2020",
                "Spring2021", "Summer2021", "Fall2021",
                "Spring2022", "Summer2022", "Fall2022",
                "Spring2023", "Summer2023", "Fall2023",
                "Spring2024", "Summer2024", "Fall2024"
        );

        for (String semesterName : semesterNames) {
            Semester semester = new Semester();
            semester.setName(semesterName);
            semesterRepository.save(semester);
        }
    }

    @Transactional
    private void seedStudentFieldData() {
        // Create Department
        Department itDepartment = Department.builder()
                .name("Information Technology")
                .code("IT")
                .build();
        departmentRepository.save(itDepartment);

        Department businessAdministrationDepartment = Department.builder()
                .name("Business Administration")
                .code("BA")
                .build();
        departmentRepository.save(businessAdministrationDepartment);

        Department communicationTechnologyDepartment = Department.builder()
                .name("Communication Technology")
                .code("CT")
                .build();
        departmentRepository.save(communicationTechnologyDepartment);

        Department languageDepartment = Department.builder()
                .name("Languages")
                .code("LANG")
                .build();
        departmentRepository.save(languageDepartment);

        // Create Major and assign to Department
        Major softwareEngineering = Major.builder()
                .name("Software Engineering")
                .code("SE")
                .department(itDepartment)
                .build();
        majorRepository.save(softwareEngineering);

        Major informationSecurity = Major.builder()
                .name("Information Security")
                .code("IS")
                .department(itDepartment)
                .build();
        majorRepository.save(informationSecurity);

        Major informationSystems = Major.builder()
                .name("Information Systems")
                .code("ISY")
                .department(itDepartment)
                .build();
        majorRepository.save(informationSystems);

        Major artificialIntelligence = Major.builder()
                .name("Artificial Intelligence")
                .code("AI")
                .department(itDepartment)
                .build();
        majorRepository.save(artificialIntelligence);

        // Business Administration Majors
        Major digitalMarketingBA = Major.builder()
                .name("Digital Marketing")
                .code("DM-BA")
                .department(businessAdministrationDepartment)
                .build();
        majorRepository.save(digitalMarketingBA);

        Major internationalBusinessBA = Major.builder()
                .name("International Business")
                .code("IB-BA")
                .department(businessAdministrationDepartment)
                .build();
        majorRepository.save(internationalBusinessBA);

        Major financeBA = Major.builder()
                .name("Finance")
                .code("FIN-BA")
                .department(businessAdministrationDepartment)
                .build();
        majorRepository.save(financeBA);

        // Communication Technology Majors
        Major multimediaCommunication = Major.builder()
                .name("Multimedia Communication")
                .code("MC")
                .department(communicationTechnologyDepartment)
                .build();
        majorRepository.save(multimediaCommunication);

        Major publicRelations = Major.builder()
                .name("Public Relations")
                .code("PR")
                .department(communicationTechnologyDepartment)
                .build();
        majorRepository.save(publicRelations);

        // Languages Majors
        Major englishLanguage = Major.builder()
                .name("English Language")
                .code("ENG")
                .department(languageDepartment)
                .build();
        majorRepository.save(englishLanguage);

        Major koreanLanguage = Major.builder()
                .name("Korean Language")
                .code("KOR")
                .department(languageDepartment)
                .build();
        majorRepository.save(koreanLanguage);

        Major japaneseLanguage = Major.builder()
                .name("Japanese Language")
                .code("JPN")
                .department(languageDepartment)
                .build();
        majorRepository.save(japaneseLanguage);

        Major chineseLanguage = Major.builder()
                .name("Chinese Language")
                .code("CHN")
                .department(languageDepartment)
                .build();
        majorRepository.save(chineseLanguage);

        // Create Specializations and assign to Major
        Specialization bridgeEngineer = Specialization.builder()
                .name("Bridge Engineer")
                .code("BE")
                .major(softwareEngineering)
                .build();
        Specialization nodeJsSpecialist = Specialization.builder()
                .name("NodeJS")
                .code("NJS")
                .major(softwareEngineering)
                .build();
        specializationRepository.save(bridgeEngineer);
        specializationRepository.save(nodeJsSpecialist);

        // Specializations for Information Security
        Specialization networkSecurity = Specialization.builder()
                .name("Network Security")
                .code("NS")
                .major(informationSecurity)
                .build();
        Specialization cryptography = Specialization.builder()
                .name("Cryptography")
                .code("CRY")
                .major(informationSecurity)
                .build();
        specializationRepository.save(networkSecurity);
        specializationRepository.save(cryptography);

        // Specializations for Information Systems
        Specialization databaseManagement = Specialization.builder()
                .name("Database Management")
                .code("DBM")
                .major(informationSystems)
                .build();
        Specialization enterpriseSystems = Specialization.builder()
                .name("Enterprise Systems")
                .code("ES")
                .major(informationSystems)
                .build();
        specializationRepository.save(databaseManagement);
        specializationRepository.save(enterpriseSystems);

        // Specializations for Artificial Intelligence
        Specialization machineLearning = Specialization.builder()
                .name("Machine Learning")
                .code("ML")
                .major(artificialIntelligence)
                .build();
        Specialization naturalLanguageProcessing = Specialization.builder()
                .name("Natural Language Processing")
                .code("NLP")
                .major(artificialIntelligence)
                .build();
        specializationRepository.save(machineLearning);
        specializationRepository.save(naturalLanguageProcessing);

        // Chuyên ngành cho Ngành Quản trị Kinh doanh
        Specialization internationalTrade = Specialization.builder()
                .name("International Trade")
                .code("IT")
                .major(internationalBusinessBA)
                .build();
        Specialization globalSupplyChainManagement = Specialization.builder()
                .name("Global Supply Chain Management")
                .code("GSCM")
                .major(internationalBusinessBA)
                .build();
        Specialization corporateFinance = Specialization.builder()
                .name("Corporate Finance")
                .code("CF")
                .major(financeBA)
                .build();
        Specialization investmentBanking = Specialization.builder()
                .name("Investment Banking")
                .code("IB")
                .major(financeBA)
                .build();
        Specialization socialMediaMarketing = Specialization.builder()
                .name("Social Media Marketing")
                .code("SMM")
                .major(digitalMarketingBA)
                .build();
        Specialization contentMarketing = Specialization.builder()
                .name("Content Marketing")
                .code("CM")
                .major(digitalMarketingBA)
                .build();

        specializationRepository.save(internationalTrade);
        specializationRepository.save(globalSupplyChainManagement);
        specializationRepository.save(corporateFinance);
        specializationRepository.save(investmentBanking);
        specializationRepository.save(socialMediaMarketing);
        specializationRepository.save(contentMarketing);

        // Thêm chuyên ngành cho Multimedia Communication
        Specialization graphicDesign = Specialization.builder()
                .name("Graphic Design")
                .code("GD")
                .major(multimediaCommunication)
                .build();
        specializationRepository.save(graphicDesign);

        // Thêm chuyên ngành cho Public Relations
        Specialization mediaRelations = Specialization.builder()
                .name("Media Relations")
                .code("MR")
                .major(publicRelations)
                .build();
        specializationRepository.save(mediaRelations);

        // Chuyên ngành cho Ngành Ngôn ngữ
        Specialization translationAndInterpretation = Specialization.builder()
                .name("Translation and Interpretation")
                .code("TI")
                .major(englishLanguage)
                .build();
        Specialization englishLiterature = Specialization.builder()
                .name("English Literature")
                .code("EL")
                .major(englishLanguage)
                .build();
        Specialization koreanCulture = Specialization.builder()
                .name("Korean Culture")
                .code("KC")
                .major(koreanLanguage)
                .build();
        Specialization koreanBusinessCommunication = Specialization.builder()
                .name("Korean Business Communication")
                .code("KBC")
                .major(koreanLanguage)
                .build();
        Specialization japaneseCulture = Specialization.builder()
                .name("Japanese Culture")
                .code("JC")
                .major(japaneseLanguage)
                .build();
        Specialization japaneseBusinessCommunication = Specialization.builder()
                .name("Japanese Business Communication")
                .code("JBC")
                .major(japaneseLanguage)
                .build();
        Specialization chineseCulture = Specialization.builder()
                .name("Chinese Culture")
                .code("CC")
                .major(chineseLanguage)
                .build();
        Specialization chineseBusinessCommunication = Specialization.builder()
                .name("Chinese Business Communication")
                .code("CBC")
                .major(chineseLanguage)
                .build();

        specializationRepository.save(translationAndInterpretation);
        specializationRepository.save(englishLiterature);
        specializationRepository.save(koreanCulture);
        specializationRepository.save(koreanBusinessCommunication);
        specializationRepository.save(japaneseCulture);
        specializationRepository.save(japaneseBusinessCommunication);
        specializationRepository.save(chineseCulture);
        specializationRepository.save(chineseBusinessCommunication);

        System.out.println("Seed data successfully added.");
    }

    @Transactional
    public void seedZeroTermSubjects() {
        List<Subject> zeroTermSubjects = List.of(
                Subject.builder().code("VOV114").name("Vovinam 1").credit(2).subjectType(Subject.SubjectType.IN_PROGRAM).department(null).build(),
                Subject.builder().code("VOV124").name("Vovinam 2").credit(2).subjectType(Subject.SubjectType.IN_PROGRAM).department(null).build(),
                Subject.builder().code("VOV134").name("Vovinam 3").credit(2).subjectType(Subject.SubjectType.IN_PROGRAM).department(null).build(),
                Subject.builder().code("TRS601").name("English 6 (University Success)").credit(0).subjectType(Subject.SubjectType.IN_PROGRAM).department(null).build(),
                Subject.builder().code("TMI101").name("Traditional Musical Instrument").credit(3).subjectType(Subject.SubjectType.IN_PROGRAM).department(null).build()
        );

        subjectRepository.saveAll(zeroTermSubjects);
    }

    @Transactional
    public void seedSubjectsForITDepartment() {
        Department itDepartment = departmentRepository.findByName("Information Technology")
                .orElseThrow(() -> new RuntimeException("Department not found: Information Technology"));

        List<Subject> subjects = List.of(
                Subject.builder().code("PRF192").name("Programming Fundamentals").credit(3).subjectType(Subject.SubjectType.IN_PROGRAM).department(itDepartment).build(),
                Subject.builder().code("MAE101").name("Mathematics for Engineering").credit(3).subjectType(Subject.SubjectType.IN_PROGRAM).department(itDepartment).build(),
                Subject.builder().code("CEA201").name("Computer Organization and Architecture").credit(3).subjectType(Subject.SubjectType.IN_PROGRAM).department(itDepartment).build(),
                Subject.builder().code("SSL101c").name("Academic Skills for University Success").credit(3).subjectType(Subject.SubjectType.IN_PROGRAM).department(itDepartment).build(),
                Subject.builder().code("CSI104").name("Introduction to Computing").credit(3).subjectType(Subject.SubjectType.IN_PROGRAM).department(itDepartment).build(),
                Subject.builder().code("PRO192").name("Object-Oriented Programming").credit(3).subjectType(Subject.SubjectType.IN_PROGRAM).department(itDepartment).build(),
                Subject.builder().code("MAD101").name("Discrete Mathematics").credit(3).subjectType(Subject.SubjectType.IN_PROGRAM).department(itDepartment).build(),
                Subject.builder().code("OSG202").name("Operating Systems").credit(3).subjectType(Subject.SubjectType.IN_PROGRAM).department(itDepartment).build(),
                Subject.builder().code("NWC203c").name("Computer Networking").credit(3).subjectType(Subject.SubjectType.IN_PROGRAM).department(itDepartment).build(),
                Subject.builder().code("SSG104").name("Communication and In-Group Working Skills").credit(3).subjectType(Subject.SubjectType.IN_PROGRAM).department(itDepartment).build(),
                Subject.builder().code("CSD201").name("Data Structures and Algorithms").credit(3).subjectType(Subject.SubjectType.IN_PROGRAM).department(itDepartment).build(),
                Subject.builder().code("DBI202").name("Database Systems").credit(3).subjectType(Subject.SubjectType.IN_PROGRAM).department(itDepartment).build(),
                Subject.builder().code("LAB211").name("OOP with Java Lab").credit(3).subjectType(Subject.SubjectType.IN_PROGRAM).department(itDepartment).build(),
                Subject.builder().code("JPD113").name("Elementary Japanese 1- A1.1").credit(3).subjectType(Subject.SubjectType.IN_PROGRAM).department(itDepartment).build(),
                Subject.builder().code("WED201c").name("Web Design").credit(3).subjectType(Subject.SubjectType.IN_PROGRAM).department(itDepartment).build(),
                Subject.builder().code("SWE201c").name("Introduction to Software Engineering").credit(3).subjectType(Subject.SubjectType.IN_PROGRAM).department(itDepartment).build(),
                Subject.builder().code("JPD123").name("Elementary Japanese 1-A1.2").credit(3).subjectType(Subject.SubjectType.IN_PROGRAM).department(itDepartment).build(),
                Subject.builder().code("MAS291").name("Statistics & Probability").credit(3).subjectType(Subject.SubjectType.IN_PROGRAM).department(itDepartment).build(),
                Subject.builder().code("IOT102").name("Internet of Things").credit(3).subjectType(Subject.SubjectType.IN_PROGRAM).department(itDepartment).build(),
                Subject.builder().code("PRJ301").name("Java Web Application Development").credit(3).subjectType(Subject.SubjectType.IN_PROGRAM).department(itDepartment).build(),
                Subject.builder().code("SWR302").name("Software Requirements").credit(3).subjectType(Subject.SubjectType.IN_PROGRAM).department(itDepartment).build(),
                Subject.builder().code("SWT301").name("Software Testing").credit(3).subjectType(Subject.SubjectType.IN_PROGRAM).department(itDepartment).build(),
                Subject.builder().code("SWP391").name("Software Development Project").credit(3).subjectType(Subject.SubjectType.IN_PROGRAM).department(itDepartment).build(),
                Subject.builder().code("ITE302c").name("Ethics in IT").credit(3).subjectType(Subject.SubjectType.IN_PROGRAM).department(itDepartment).build(),
                Subject.builder().code("OJT202").name("On the Job Training").credit(10).subjectType(Subject.SubjectType.IN_PROGRAM).department(itDepartment).build(),
                Subject.builder().code("SWD392").name("Software Architecture and Design").credit(3).subjectType(Subject.SubjectType.IN_PROGRAM).department(itDepartment).build(),
                Subject.builder().code("PMG201c").name("Project Management").credit(3).subjectType(Subject.SubjectType.IN_PROGRAM).department(itDepartment).build(),
                Subject.builder().code("MLN122").name("Political Economics of Marxism–Leninism").credit(2).subjectType(Subject.SubjectType.IN_PROGRAM).department(itDepartment).build(),
                Subject.builder().code("MLN111").name("Philosophy of Marxism–Leninism").credit(3).subjectType(Subject.SubjectType.IN_PROGRAM).department(itDepartment).build(),
                Subject.builder().code("PRM392").name("Mobile Programming").credit(3).subjectType(Subject.SubjectType.IN_PROGRAM).department(itDepartment).build(),
                Subject.builder().code("WDU203c").name("The UI/UX Design").credit(3).subjectType(Subject.SubjectType.IN_PROGRAM).department(itDepartment).build(),
                Subject.builder().code("MLN131").name("Scientific Socialism").credit(0).subjectType(Subject.SubjectType.IN_PROGRAM).department(itDepartment).build(),
                Subject.builder().code("VNR202").name("History of the Vietnam Communist Party").credit(2).subjectType(Subject.SubjectType.IN_PROGRAM).department(itDepartment).build(),
                Subject.builder().code("HCM202").name("Ho Chi Minh Ideology").credit(2).subjectType(Subject.SubjectType.IN_PROGRAM).department(itDepartment).build(),
                Subject.builder().code("SEP490").name("SE Capstone Project").credit(0).subjectType(Subject.SubjectType.IN_PROGRAM).department(itDepartment).build(),

                // Subjects for Bridge Engineer
                Subject.builder().code("JPD133").name("Elementary Japanese 1-A1/A2").credit(3).subjectType(Subject.SubjectType.IN_PROGRAM).department(itDepartment).build(),
                Subject.builder().code("JPD316").name("Japanese Intermediate 1-B1/B2").credit(6).subjectType(Subject.SubjectType.IN_PROGRAM).department(itDepartment).build(),
                Subject.builder().code("JPD326").name("Japanese Intermediate 2-B2.1").credit(6).subjectType(Subject.SubjectType.IN_PROGRAM).department(itDepartment).build(),
                Subject.builder().code("SYB302c").name("Entrepreneurship").credit(3).subjectType(Subject.SubjectType.IN_PROGRAM).department(itDepartment).build(),

                // Subjects for Node.js
                Subject.builder().code("FER201m").name("Frontend Development with React").credit(5).subjectType(Subject.SubjectType.IN_PROGRAM).department(itDepartment).build(),
                Subject.builder().code("SDN301m").name("Server-side Development with Node.js").credit(7).subjectType(Subject.SubjectType.IN_PROGRAM).department(itDepartment).build(),
                Subject.builder().code("ISC301").name("Introduction to Cloud Computing").credit(8).subjectType(Subject.SubjectType.IN_PROGRAM).department(itDepartment).build(),
                Subject.builder().code("MMA301").name("Microservices and APIs").credit(7).subjectType(Subject.SubjectType.IN_PROGRAM).department(itDepartment).build()
        );

        subjectRepository.saveAll(subjects);
    }

    @Transactional
    public void seedElectiveSubjects() {
        List<Subject> electiveSubjects = List.of(
                Subject.builder().code("SE-0001").name("Elective 1").credit(3).subjectType(Subject.SubjectType.ELECTIVE).department(null).build(),
                Subject.builder().code("SE-0002").name("Elective 2").credit(3).subjectType(Subject.SubjectType.ELECTIVE).department(null).build(),
                Subject.builder().code("SE-0003").name("Elective 3").credit(3).subjectType(Subject.SubjectType.ELECTIVE).department(null).build(),
                Subject.builder().code("SE-0004").name("Elective 4").credit(3).subjectType(Subject.SubjectType.ELECTIVE).department(null).build()
        );

        subjectRepository.saveAll(electiveSubjects);
    }

    @Transactional
    public void seedSubjectsForSoftwareEngineeringMajor() {
        Major softwareEngineering = majorRepository.findByName("Software Engineering")
                .orElseThrow(() -> new RuntimeException("Major not found"));

        Map<String, Integer> subjectTermMap = new HashMap<>();

        subjectTermMap.put("SE-0001", 5);
        subjectTermMap.put("SE-0002", 7);
        subjectTermMap.put("SE-0003", 7);
        subjectTermMap.put("SE-0004", 8);

        subjectTermMap.put("PRF192", 1);
        subjectTermMap.put("MAE101", 1);
        subjectTermMap.put("CEA201", 1);
        subjectTermMap.put("SSL101c", 1);
        subjectTermMap.put("CSI104", 1);
        subjectTermMap.put("PRO192", 2);
        subjectTermMap.put("MAD101", 2);
        subjectTermMap.put("OSG202", 2);
        subjectTermMap.put("NWC203c", 2);
        subjectTermMap.put("SSG104", 2);
        subjectTermMap.put("CSD201", 3);
        subjectTermMap.put("DBI202", 3);
        subjectTermMap.put("LAB211", 3);
        subjectTermMap.put("JPD113", 3);
        subjectTermMap.put("WED201c", 3);
        subjectTermMap.put("SWE201c", 4);
        subjectTermMap.put("JPD123", 4);
        subjectTermMap.put("MAS291", 4);
        subjectTermMap.put("IOT102", 4);
        subjectTermMap.put("PRJ301", 4);
        subjectTermMap.put("SWR302", 5);
        subjectTermMap.put("SWT301", 5);
        subjectTermMap.put("SWP391", 5);
        subjectTermMap.put("ITE302c", 5);
        subjectTermMap.put("JPD133", 5);
        subjectTermMap.put("OJT202", 6);
        subjectTermMap.put("SWD392", 7);
        subjectTermMap.put("PMG201c", 7);
        subjectTermMap.put("MLN122", 8);
        subjectTermMap.put("MLN111", 8);
        subjectTermMap.put("PRM392", 8);
        subjectTermMap.put("WDU203c", 8);
        subjectTermMap.put("MLN131", 9);
        subjectTermMap.put("VNR202", 9);
        subjectTermMap.put("HCM202", 9);
        subjectTermMap.put("SEP490", 9);

        // Get list of subject codes from the map
        List<String> subjectCodes = List.copyOf(subjectTermMap.keySet());

        // Find all subjects with these codes
        List<Subject> subjects = subjectRepository.findByCodeIn(subjectCodes);

        List<SubjectForMajor> subjectsForSoftwareEngineering = subjects.stream()
                .map(subject -> SubjectForMajor.builder()
                        .subject(subject)
                        .major(softwareEngineering)
                        .term(subjectTermMap.get(subject.getCode()))
                        .build())
                .collect(Collectors.toList());

        subjectForMajorRepository.saveAll(subjectsForSoftwareEngineering);
    }

    @Transactional
    public void seedSubjectsForBridgeEngineerSpecialization() {
        Specialization bridgeEngineer = specializationRepository.findByName("Bridge Engineer")
                .orElseThrow(() -> new RuntimeException("Specialization not found: Bridge Engineer"));

        // Mapping of subjects and their respective terms for Bridge Engineer specialization
        Map<String, Integer> subjectTermMap = Map.of(
                "JPD133", 5,
                "JPD316", 7,
                "JPD326", 8,
                "SYB302c", 7
        );

        // Get list of subject codes for the Bridge Engineer specialization
        List<String> subjectCodes = List.copyOf(subjectTermMap.keySet());

        // Fetch subjects by codes
        List<Subject> subjects = subjectRepository.findByCodeIn(subjectCodes);

        // Create SubjectForSpecialization entries
        List<SubjectForSpecialization> subjectsForBridgeEngineer = subjects.stream()
                .map(subject -> SubjectForSpecialization.builder()
                        .subject(subject)
                        .specialization(bridgeEngineer)
                        .term(subjectTermMap.get(subject.getCode()))
                        .build())
                .collect(Collectors.toList());

        subjectForSpecializationRepository.saveAll(subjectsForBridgeEngineer);
    }

    @Transactional
    public void seedSubjectsForNodeJSSpecialization() {
        Specialization bridgeEngineer = specializationRepository.findByName("NodeJS")
                .orElseThrow(() -> new RuntimeException("Specialization not found: Bridge Engineer"));

        // Mapping of subjects and their respective terms for Bridge Engineer specialization
        Map<String, Integer> subjectTermMap = Map.of(
                "FER201m", 5,
                "SDN301m", 7,
                "ISC301", 7,
                "MMA301", 8
        );

        // Get list of subject codes for the Bridge Engineer specialization
        List<String> subjectCodes = List.copyOf(subjectTermMap.keySet());

        // Fetch subjects by codes
        List<Subject> subjects = subjectRepository.findByCodeIn(subjectCodes);

        // Create SubjectForSpecialization entries
        List<SubjectForSpecialization> subjectsForBridgeEngineer = subjects.stream()
                .map(subject -> SubjectForSpecialization.builder()
                        .subject(subject)
                        .specialization(bridgeEngineer)
                        .term(subjectTermMap.get(subject.getCode()))
                        .build())
                .collect(Collectors.toList());

        subjectForSpecializationRepository.saveAll(subjectsForBridgeEngineer);
    }

    private void seedStudents() {
        Department itDepartment = departmentRepository.findByName("Information Technology").orElseThrow();
        Major softwareEngineering = majorRepository.findByName("Software Engineering").orElseThrow();
        Specialization bridgeEngineer = specializationRepository.findByName("Bridge Engineer").orElseThrow();
        Specialization nodeJsSpecialist = specializationRepository.findByName("NodeJS").orElseThrow();

        List<Student> students = List.of(
                // Male Students (Index 1-10)
                createStudent(1, "SE1001", "k18", "John Doe", softwareEngineering, 2, itDepartment, null),
                createStudent(2, "SE170042", "K17", "Trình Vĩnh Phát", softwareEngineering, 9, itDepartment, nodeJsSpecialist),
                createStudent(3, "SE170440", "K17", "Đoàn Tiến Phát", softwareEngineering, 9, itDepartment, nodeJsSpecialist),
                createStudent(4, "SE170225", "K17", "Vũ Ngọc Hải Đăng", softwareEngineering, 9, itDepartment, nodeJsSpecialist),
                createStudent(5, "SE170431", "K17", "Nguyễn An Khánh", softwareEngineering, 9, itDepartment, nodeJsSpecialist),
                createStudent(11, "SE1002", "k18", "Michael Smith", softwareEngineering, 3, itDepartment, null),
                createStudent(12, "SE1003", "k18", "David Brown", softwareEngineering, 4, itDepartment, null),
                createStudent(13, "SE1004", "k19", "Robert White", softwareEngineering, 2, itDepartment, null),
                createStudent(14, "SE1005", "k19", "James Miller", softwareEngineering, 4, itDepartment, null),
                createStudent(6, "SE1006", "k20", "William Johnson", softwareEngineering, 6, itDepartment, bridgeEngineer),
                createStudent(7, "SE1007", "k20", "Thomas Anderson", softwareEngineering, 7, itDepartment, nodeJsSpecialist),
                createStudent(8, "SE1008", "k17", "Charles Martinez", softwareEngineering, 9, itDepartment, bridgeEngineer),
                createStudent(9, "SE1009", "k17", "Daniel Lewis", softwareEngineering, 9, itDepartment, nodeJsSpecialist),
                createStudent(10, "SE1010", "k17", "Matthew Clark", softwareEngineering, 9, itDepartment, bridgeEngineer),


                // Female Students (Index 11-20)
                createStudent(15, "SE1011", "k18", "Emma Walker", softwareEngineering, 2, itDepartment, null),
                createStudent(16, "SE1012", "k18", "Olivia Robinson", softwareEngineering, 3, itDepartment, null),
                createStudent(17, "SE1013", "k18", "Ava Scott", softwareEngineering, 4, itDepartment, null),
                createStudent(18, "SE1014", "k19", "Sophia Green", softwareEngineering, 2, itDepartment, null),
                createStudent(19, "SE1015", "k19", "Isabella Harris", softwareEngineering, 4, itDepartment, null),
                createStudent(20, "SE1016", "k20", "Mia Adams", softwareEngineering, 6, itDepartment, bridgeEngineer),
                createStudent(21, "SE1017", "k20", "Charlotte Campbell", softwareEngineering, 7, itDepartment, nodeJsSpecialist),
                createStudent(22, "SE1018", "k17", "Amelia Mitchell", softwareEngineering, 9, itDepartment, bridgeEngineer),
                createStudent(23, "SE1019", "k17", "Evelyn Carter", softwareEngineering, 9, itDepartment, nodeJsSpecialist),
                createStudent(24, "SE1020", "k17", "Abigail Roberts", softwareEngineering, 9, itDepartment, bridgeEngineer)
        );

        studentRepository.saveAll(students);

        // Seed for Information Security
        Major infoSecurity = majorRepository.findByName("Information Security").orElseThrow();
        Specialization networkSecurity = specializationRepository.findByName("Network Security").orElseThrow();
        Specialization cryptography = specializationRepository.findByName("Cryptography").orElseThrow();

        List<Student> infoSecurityStudents = List.of(
                createStudent(25, "IS1001", "k18", "Ethan Moore", infoSecurity, 2, itDepartment, null),
                createStudent(26, "IS1002", "k18", "Logan Taylor", infoSecurity, 3, itDepartment, null),
                createStudent(27, "IS1003", "k18", "Alexander Lee", infoSecurity, 5, itDepartment, null),
                createStudent(28, "IS1004", "k19", "Sebastian Hall", infoSecurity, 6, itDepartment, networkSecurity),
                createStudent(29, "IS1005", "k19", "Jackson Young", infoSecurity, 7, itDepartment, cryptography),
                createStudent(30, "IS1006", "k19", "Mason Hernandez", infoSecurity, 8, itDepartment, networkSecurity),
                createStudent(31, "IS1007", "k20", "Lucas King", infoSecurity, 9, itDepartment, cryptography),
                createStudent(32, "IS1008", "k20", "Liam Wright", infoSecurity, 9, itDepartment, networkSecurity),
                createStudent(33, "IS1009", "k20", "Henry Scott", infoSecurity, 9, itDepartment, cryptography),
                createStudent(34, "IS1010", "k20", "Benjamin Carter", infoSecurity, 9, itDepartment, networkSecurity)
        );

        // Seed for Information Systems
        Major infoSystems = majorRepository.findByName("Information Systems").orElseThrow();
        Specialization databaseManagement = specializationRepository.findByName("Database Management").orElseThrow();
        Specialization enterpriseSystems = specializationRepository.findByName("Enterprise Systems").orElseThrow();

        List<Student> infoSystemsStudents = List.of(
                createStudent(35, "ISY1001", "k18", "Charlotte Perez", infoSystems, 2, itDepartment, null),
                createStudent(36, "ISY1002", "k18", "Harper Thompson", infoSystems, 3, itDepartment, null),
                createStudent(37, "ISY1003", "k18", "Evelyn White", infoSystems, 5, itDepartment, null),
                createStudent(38, "ISY1004", "k19", "Avery Ramirez", infoSystems, 6, itDepartment, databaseManagement),
                createStudent(39, "ISY1005", "k19", "Scarlett Martinez", infoSystems, 7, itDepartment, enterpriseSystems),
                createStudent(40, "ISY1006", "k19", "Luna Phillips", infoSystems, 8, itDepartment, databaseManagement),
                createStudent(41, "ISY1007", "k20", "Ella Evans", infoSystems, 9, itDepartment, enterpriseSystems),
                createStudent(42, "ISY1008", "k20", "Grace Turner", infoSystems, 9, itDepartment, databaseManagement),
                createStudent(43, "ISY1009", "k20", "Chloe Parker", infoSystems, 9, itDepartment, enterpriseSystems),
                createStudent(44, "ISY1010", "k20", "Isabella Collins", infoSystems, 9, itDepartment, databaseManagement)
        );

        // Seed for Artificial Intelligence
        Major ai = majorRepository.findByName("Artificial Intelligence").orElseThrow();
        Specialization machineLearning = specializationRepository.findByName("Machine Learning").orElseThrow();
        Specialization nlp = specializationRepository.findByName("Natural Language Processing").orElseThrow();

        List<Student> aiStudents = List.of(
                createStudent(45, "AI1001", "k18", "Oliver Brown", ai, 2, itDepartment, null),
                createStudent(46, "AI1002", "k18", "James Harris", ai, 3, itDepartment, null),
                createStudent(47, "AI1003", "k18", "William Jones", ai, 5, itDepartment, null),
                createStudent(48, "AI1004", "k19", "Henry Wilson", ai, 6, itDepartment, machineLearning),
                createStudent(49, "AI1005", "k19", "Leo Robinson", ai, 7, itDepartment, nlp),
                createStudent(50, "AI1006", "k19", "Jack Walker", ai, 8, itDepartment, machineLearning),
                createStudent(51, "AI1007", "k20", "Luke Allen", ai, 9, itDepartment, nlp),
                createStudent(52, "AI1008", "k20", "Alexander Cook", ai, 9, itDepartment, machineLearning),
                createStudent(53, "AI1009", "k20", "Jacob Kelly", ai, 9, itDepartment, nlp),
                createStudent(54, "AI1010", "k20", "Thomas Reed", ai, 9, itDepartment, machineLearning)
        );

        // Save all students
        studentRepository.saveAll(infoSecurityStudents);
        studentRepository.saveAll(infoSystemsStudents);
        studentRepository.saveAll(aiStudents);

        Major digitalMarketingBA = majorRepository.findByName("Digital Marketing").orElseThrow();
        Major internationalBusinessBA = majorRepository.findByName("International Business").orElseThrow();
        Major financeBA = majorRepository.findByName("Finance").orElseThrow();
        Major multimediaCommunication = majorRepository.findByName("Multimedia Communication").orElseThrow();
        Major publicRelations = majorRepository.findByName("Public Relations").orElseThrow();
        Major englishLanguage = majorRepository.findByName("English Language").orElseThrow();
        Major koreanLanguage = majorRepository.findByName("Korean Language").orElseThrow();
        Major japaneseLanguage = majorRepository.findByName("Japanese Language").orElseThrow();
        Major chineseLanguage = majorRepository.findByName("Chinese Language").orElseThrow();

        Specialization socialMediaMarketing = specializationRepository.findByName("Social Media Marketing").orElseThrow();
        Specialization contentMarketing = specializationRepository.findByName("Content Marketing").orElseThrow();
        Specialization internationalTrade = specializationRepository.findByName("International Trade").orElseThrow();
        Specialization globalSupplyChainManagement = specializationRepository.findByName("Global Supply Chain Management").orElseThrow();
        Specialization corporateFinance = specializationRepository.findByName("Corporate Finance").orElseThrow();
        Specialization investmentBanking = specializationRepository.findByName("Investment Banking").orElseThrow();
        Specialization graphicDesign = specializationRepository.findByName("Graphic Design").orElseThrow();
        Specialization mediaRelations = specializationRepository.findByName("Media Relations").orElseThrow();
        Specialization translationAndInterpretation = specializationRepository.findByName("Translation and Interpretation").orElseThrow();
        Specialization englishLiterature = specializationRepository.findByName("English Literature").orElseThrow();
        Specialization koreanCulture = specializationRepository.findByName("Korean Culture").orElseThrow();
        Specialization koreanBusinessCommunication = specializationRepository.findByName("Korean Business Communication").orElseThrow();
        Specialization japaneseCulture = specializationRepository.findByName("Japanese Culture").orElseThrow();
        Specialization japaneseBusinessCommunication = specializationRepository.findByName("Japanese Business Communication").orElseThrow();
        Specialization chineseCulture = specializationRepository.findByName("Chinese Culture").orElseThrow();
        Specialization chineseBusinessCommunication = specializationRepository.findByName("Chinese Business Communication").orElseThrow();

        // Seed students for each Major
        seedStudentsForMajor(100, digitalMarketingBA, socialMediaMarketing, contentMarketing);
        seedStudentsForMajor(200, internationalBusinessBA, internationalTrade, globalSupplyChainManagement);
        seedStudentsForMajor(300, financeBA, corporateFinance, investmentBanking);
        seedStudentsForMajor(400, multimediaCommunication, graphicDesign, null); // only 1 specialization
        seedStudentsForMajor(500, publicRelations, mediaRelations, null); // only 1 specialization
        seedStudentsForMajor(600, englishLanguage, translationAndInterpretation, englishLiterature);
        seedStudentsForMajor(700, koreanLanguage, koreanCulture, koreanBusinessCommunication);
        seedStudentsForMajor(800, japaneseLanguage, japaneseCulture, japaneseBusinessCommunication);
        seedStudentsForMajor(900, chineseLanguage, chineseCulture, chineseBusinessCommunication);
    }

    private void seedStudentsForMajor(int number, Major major, Specialization specialization1, Specialization specialization2) {
        // 3 students without specialization (not in the 6th semester)
        studentRepository.save(createStudent(number + 1, major.getCode() + "01", "k17", "Student 1", major, 2, major.getDepartment(), null));
        studentRepository.save(createStudent(number + 2, major.getCode() + "02", "k18", "Student 2", major, 2, major.getDepartment(), null));
        studentRepository.save(createStudent(number + 3, major.getCode() + "03", "k19", "Student 3", major, 2, major.getDepartment(), null));

        // 7 students with specialization
        studentRepository.save(createStudent(number + 4, major.getCode() + "04", "k19", "Student 4", major, 2, major.getDepartment(), specialization1));
        studentRepository.save(createStudent(number + 5, major.getCode() + "05", "k20", "Student 5", major, 2, major.getDepartment(), specialization1));
        studentRepository.save(createStudent(number + 6, major.getCode() + "06", "k20", "Student 6", major, 2, major.getDepartment(), specialization2));
//        studentRepository.save(createStudent(number + 7, major.getCode() + "07", "k20", "Student 7", major, 7, major.getDepartment(), specialization2));
//        studentRepository.save(createStudent(number + 8, major.getCode() + "08", "k21", "Student 8", major, 8, major.getDepartment(), specialization1));
//        studentRepository.save(createStudent(number + 9, major.getCode() + "09", "k21", "Student 9", major, 8, major.getDepartment(), specialization2));
//        studentRepository.save(createStudent(number + 10, major.getCode() + "10", "k22", "Student 10", major, 9, major.getDepartment(), specialization1));
    }

    private Student createStudent(int index, String studentCode, String batch, String fullName, Major major, int currentTerm, Department department, Specialization specialization) {
        String email = ((index % 2 == 0) ? "sm" + index : "sf" + index);
        Gender gender = ((index % 2 != 0)? Gender.MALE : Gender.FEMALE);
        email += "@gmail.com";

        if(index == 2) {
            email = "phattvse170042@fpt.edu.vn";
        }
        if(index == 3) {
            email = "phatdtse170440@fpt.edu.vn";
        }
        if(index == 4) {
            email = "dangvnhse170225@fpt.edu.vn";
        }
        if(index == 5) {
            email = "khanhnase170431@fpt.edu.vn";
        }

        return Student.builder()
                .studentCode(studentCode)
                .batch(batch)
                .email(email)
                .fullName(fullName)
                .major(major)
                .currentTerm(currentTerm)
                .department(department)
                .specialization(currentTerm < 5 ? null : specialization) // Specialization null if currentTerm < 5
                .gender(gender)
                .phoneNumber("1234567890")
                .avatarLink(gender == Gender.MALE ? "https://png.pngtree.com/png-vector/20240204/ourlarge/pngtree-avatar-job-student-flat-portrait-of-man-png-image_11606889.png" : "https://thumbs.dreamstime.com/z/girl-avatar-face-student-schoolgirl-isolated-white-background-cartoon-style-vector-illustration-233213085.jpg")
                .dateOfBirth(LocalDate.of(2000, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
                .address("123 Nguyen Xien")
                .build();
    }

    private void saveBatchForStudySeeding(List<Study> batch) {
        if (!batch.isEmpty()) {
            studyRepository.saveAll(batch);
            studyRepository.flush(); // Đảm bảo dữ liệu được đẩy ngay vào DB
            batch.clear(); // Xóa danh sách để chuẩn bị cho batch tiếp theo
        }
    }

    private void seedStudies() {
        Random random = new Random();
        List<Student> students = studentRepository.findAll();
        List<String> semesters = List.of(
                "Spring2018", "Summer2018", "Fall2018",
                "Spring2019", "Summer2019", "Fall2019",
                "Spring2020", "Summer2020", "Fall2020",
                "Spring2021", "Summer2021", "Fall2021",
                "Spring2022", "Summer2022", "Fall2022",
                "Spring2023", "Summer2023", "Fall2023",
                "Spring2024", "Summer2024", "Fall2024"
        );

        Semester fall2024 = semesterRepository.findByName("Fall2024").orElseThrow();
//        Student student = students.getFirst();
        int index = 0;

        List<Study> batch = new ArrayList<>();

        for (Student student : students) {
//            if(index == 5) {
//                break;
//            }
            int currentTerm = student.getCurrentTerm();
            int semesterIndex = semesters.indexOf("Fall2024");

            // Subjects based on Major
            Major softwareEngineering = majorRepository.findByName("Software Engineering").orElseThrow();
//            List<SubjectForMajor> subjectsForMajor = subjectForMajorRepository.findByMajor(student.getMajor());
            List<SubjectForMajor> subjectsForMajor = subjectForMajorRepository.findByMajor(softwareEngineering);
            for (SubjectForMajor subjectForMajor : subjectsForMajor) {
                Semester semester = determineSemester(subjectForMajor.getTerm(), currentTerm, semesterIndex, semesters);
                Study study = createStudyEntry(student, subjectForMajor.getSubject(), currentTerm, subjectForMajor.getTerm(), semester, random);
//                studyRepository.save(study);
                batch.add(study);
                if (batch.size() >= BATCH_SIZE) {
                    saveBatchForStudySeeding(batch);
                }
            }

            // Additional subjects based on Specialization if student has a specialization
            if (student.getSpecialization() != null) {
                List<SubjectForSpecialization> subjectsForSpecialization = subjectForSpecializationRepository.findBySpecialization(student.getSpecialization());
                for (SubjectForSpecialization subjectForSpecialization : subjectsForSpecialization) {
                    Semester semester = determineSemester(subjectForSpecialization.getTerm(), currentTerm, semesterIndex, semesters);
                    Study study = createStudyEntry(student, subjectForSpecialization.getSubject(), currentTerm, subjectForSpecialization.getTerm(), semester, random);
//                    studyRepository.save(study);
                    batch.add(study);
                    if (batch.size() >= BATCH_SIZE) {
                        saveBatchForStudySeeding(batch);
                    }
                }
            } else {
                // Elective subjects for students without specialization
//                List<Subject> electiveSubjects = subjectRepository.findBySubjectType(Subject.SubjectType.ELECTIVE);
//                for (Subject electiveSubject : electiveSubjects) {
//                    Semester semester = determineSemester(electiveSubject.getCredit(), currentTerm, semesterIndex, semesters);
//                    Study study = createStudyEntry(student, electiveSubject, currentTerm, electiveSubject.getCredit(), semester, random);
//                    studyRepository.save(study);
//                }
            }
            index++;
        }
        saveBatchForStudySeeding(batch);
    }

    private Semester determineSemester(int subjectTerm, int currentTerm, int semesterIndex, List<String> semesters) {
        if (subjectTerm == currentTerm) {
            return semesterRepository.findByName("Fall2024").orElse(null);
        } else if (subjectTerm < currentTerm) {
            int offset = currentTerm - subjectTerm;
            String semesterName = semesters.get(Math.max(semesterIndex - offset, 0));
            return semesterRepository.findByName(semesterName).orElse(null);
        }
        return null; // Future terms
    }

    private Study createStudyEntry(Student student, Subject subject, int studentCurrentTerm, int subjectTerm, Semester semester, Random random) {
        BigDecimal grade = BigDecimal.valueOf(random.nextDouble() * 10); // Grade from 0 to 10

        // Determine Study Status
        StudyStatus status;
        if (subjectTerm < studentCurrentTerm) {
            status = grade.compareTo(BigDecimal.valueOf(5)) < 0 ? StudyStatus.NOT_PASSED : StudyStatus.PASSED;
        } else if (subjectTerm == studentCurrentTerm) {
            status = StudyStatus.STUDYING;
        } else {
            status = StudyStatus.NOT_STARTED;
        }

        return Study.builder()
                .grade(subjectTerm >= studentCurrentTerm ? null : grade)
                .status(status)
                .term(subjectTerm)
                .student(student)
                .subject(subject)
                .semester(semester)
                .build();
    }

    public Map<String, List<String>> generateLecturerComments() {
        Map<String, List<String>> lecturerComments = new HashMap<>();

        // Dữ liệu các tag
        List<String> studyTags = List.of(
                "Easily Distracted by Surroundings", "Does Not Follow Lecture", "Frequent Mental Distractions",
                "Lack of Eye Contact", "Constantly Looking Around", "Not Engaged in the Discussion",
                "Attention Wanders During Lectures", "Gazing Outside",
                "Frequent Tardiness", "Skipping Mandatory Classes", "Skipping Exams or Quizzes",
                "Repeatedly Ignoring Attendance Policy", "Violating Computer Usage Policy",
                "Unauthorized Use of Mobile in Class", "Breaking Dress Code",
                "Violating Lab Safety Rules", "Leaving Class Early", "Frequent Late Submissions",
                "Refuses to Answer Questions", "Avoiding Group Discussions", "Not Participating in Group Assignments",
                "Ignoring Class Activities", "Does Not Initiate Work", "Procrastinating on Assignments",
                "Lack of Initiative", "Submits Only the Bare Minimum", "Doing the Bare Minimum in Group Work",
                "Interrupting Teacher’s Explanation", "Talking During Lecture", "Distracting Others",
                "Disrupting Classroom Discussions", "Starting Arguments with Peers",
                "Inappropriate Behavior in Class", "Speaking Loudly", "Throwing Objects in Class",
                "Acting Playful During Serious Moments", "Disrupting the Flow of the Lesson",
                "Not Taking Class Seriously", "Not Preparing for Classes", "Neglecting to Complete Assignments",
                "Delaying Assignment Submissions", "Refuses to Do Homework", "No Effort in Group Work",
                "Missed Deadlines", "Minimal Effort in Project Work", "Not Trying to Improve",
                "Does Not Seek Clarification", "Lack of Communication in Team", "Not Sharing Ideas with Team",
                "Not Listening to Team Members", "Avoiding Team Work", "Does Not Contribute in Team Meetings",
                "Lack of Team Spirit", "Refuses to Collaborate on Projects", "Undermines Team Decisions",
                "Does Not Support Team Members", "Does Not Help When Required",
                "Interrupting Others in Discussions", "Not Acknowledging Peers’ Ideas", "Disrespecting Teacher’s Authority",
                "Not Following Classroom Etiquette", "Talking Over Others", "Ignoring Classroom Norms",
                "Being Disrespectful to Peers", "Making Disparaging Remarks", "Disrespecting Cultural Sensitivities",
                "Using Offensive Language", "Frequent Mood Swings", "Signs of Anxiety",
                "Struggles with Stress Management", "Withdrawing from Others", "Lack of Motivation", "Tiredness and Fatigue",
                "Frequent Absences Due to Mental Health", "Frequent Crying Episodes", "Experiencing Panic Attacks",
                "Increased Irritability", "Difficulty Expressing Ideas Clearly", "Struggles with Public Speaking",
                "Not Asking for Help When Needed", "Limited Vocabulary", "Uses Excessive Jargon",
                "Difficulty with Written Communication", "Inability to Communicate Effectively in Group Settings",
                "Difficulty Understanding Feedback", "Inconsistent Tone of Voice", "Frequent Misunderstandings in Communication",
                "Procrastination on Deadlines", "Overcommitting to Tasks", "Mismanaging Time During Projects",
                "Not Prioritizing Work Effectively", "Failing to Meet Deadlines", "Leaving Work Until the Last Minute",
                "Wasting Time on Unimportant Tasks", "Difficulty Estimating Time Needed for Tasks",
                "Avoiding Time-Sensitive Tasks", "Difficulty Balancing Multiple Tasks"
        );

        // Tạo các bình luận với các tag phù hợp
        lecturerComments.put("Not engaged in discussions and distracted frequently.",
                List.of("Easily Distracted by Surroundings", "Not Engaged in the Discussion", "Attention Wanders During Lectures"));

        lecturerComments.put("Tends to miss deadlines and procrastinate on assignments.",
                List.of("Procrastinating on Assignments", "Missed Deadlines", "Delaying Assignment Submissions"));

        lecturerComments.put("Shows little effort in group work and avoids participation.",
                List.of("Not Participating in Group Assignments", "Avoiding Team Work", "No Effort in Group Work"));

        lecturerComments.put("Often distracted during lectures, affecting learning outcomes.",
                List.of("Easily Distracted by Surroundings", "Attention Wanders During Lectures", "Lack of Eye Contact"));

        lecturerComments.put("Has difficulty expressing ideas clearly and is often misunderstood.",
                List.of("Difficulty Expressing Ideas Clearly", "Struggles with Public Speaking", "Inconsistent Tone of Voice"));

        lecturerComments.put("Disrupts class with inappropriate behavior and interruptions.",
                List.of("Disrupting Classroom Discussions", "Inappropriate Behavior in Class", "Interrupting Teacher’s Explanation"));

        lecturerComments.put("Does not follow instructions and ignores attendance policies.",
                List.of("Skipping Mandatory Classes", "Ignoring Classroom Norms", "Repeatedly Ignoring Attendance Policy"));

        lecturerComments.put("Lack of preparation for classes leads to poor performance.",
                List.of("Not Preparing for Classes", "Does Not Initiate Work", "Neglecting to Complete Assignments"));

        lecturerComments.put("Struggles to communicate effectively in group discussions.",
                List.of("Not Listening to Team Members", "Lack of Communication in Team", "Not Sharing Ideas with Team"));

        lecturerComments.put("Fails to contribute ideas in team projects and undermines decisions.",
                List.of("Undermines Team Decisions", "Refuses to Collaborate on Projects", "Does Not Contribute in Team Meetings"));

        lecturerComments.put("Demonstrates a lack of motivation and frequently absent due to mental health.",
                List.of("Lack of Motivation", "Frequent Absences Due to Mental Health", "Signs of Anxiety"));

        lecturerComments.put("Tends to interrupt others and fails to acknowledge peers’ input.",
                List.of("Interrupting Others in Discussions", "Not Acknowledging Peers’ Ideas", "Disrespecting Teacher’s Authority"));

        lecturerComments.put("Inconsistent work ethic, often delaying tasks and missing deadlines.",
                List.of("Procrastination on Deadlines", "Missed Deadlines", "Delaying Assignment Submissions"));

        lecturerComments.put("Shows poor communication skills and has difficulty with written tasks.",
                List.of("Difficulty with Written Communication", "Struggles with Public Speaking", "Difficulty Understanding Feedback"));

        lecturerComments.put("Frequently distracts others and causes disruptions during lectures.",
                List.of("Disrupting Classroom Discussions", "Distracting Others", "Talking During Lecture"));

        lecturerComments.put("Does not actively engage in assignments and avoids contributing in group settings.",
                List.of("Avoiding Team Work", "Does Not Initiate Work", "Not Participating in Group Assignments"));

        lecturerComments.put("Demonstrates a lack of teamwork and avoids collaboration in group projects.",
                List.of("Refuses to Collaborate on Projects", "Lack of Team Spirit", "Undermines Team Decisions"));

        lecturerComments.put("Has poor time management, leading to unfinished work and missed opportunities.",
                List.of("Wasting Time on Unimportant Tasks", "Mismanaging Time During Projects", "Difficulty Estimating Time Needed for Tasks"));

        lecturerComments.put("Tends to be late and fails to meet deadlines regularly.",
                List.of("Frequent Tardiness", "Missed Deadlines", "Leaving Work Until the Last Minute"));

        lecturerComments.put("Frequently struggles with anxiety and mood swings, affecting performance.",
                List.of("Frequent Mood Swings", "Struggles with Stress Management", "Signs of Anxiety"));

        return lecturerComments;
    }

    public void seedAttendance() {
        List<Student> students = studentRepository.findAll();
        Random random = new Random();

        // Mảng bình luận giảng viên (10 tích cực, 10 tiêu cực)
        Map<String, List<String>> lecturerCommentsHashMap = generateLecturerComments();
        String[] lecturerComments = lecturerCommentsHashMap.keySet().toArray(new String[0]);

//        Student student = students.getFirst();
        int index = 0;
        for (Student student : students) {
//            if(index == 5) {
//                break;
//            }
            List<Study> studies = studyRepository.findByStudentAndStatusNot(student, StudyStatus.NOT_STARTED);
            Map<Semester, List<Study>> studiesBySemester = studies.stream().collect(Collectors.groupingBy(Study::getSemester));

            for (Map.Entry<Semester, List<Study>> entry : studiesBySemester.entrySet()) {
                Semester semester = entry.getKey();
                List<Study> semesterStudies = entry.getValue();

                LocalDate semesterStartDate = determineSemesterStartDate(semester);
                LocalDate semesterEndDate = semesterStartDate.plusDays(89); // 90 ngày cho mỗi kỳ

                for (Study study : semesterStudies) {
                    Attendance attendance = Attendance.builder()
                            .startDate(semesterStartDate)
                            .totalSlot(20)  // Mỗi môn có 20 slot phân bổ đều trong kỳ
                            .student(student)
                            .subject(study.getSubject())
                            .semester(semester)
                            .grade(study.getGrade())
                            .status(study.getStatus())
                            .build();
                    attendanceRepository.save(attendance);

                    seedAttendanceDetail(attendance, semesterStartDate, semesterEndDate, study, random, lecturerComments, lecturerCommentsHashMap, index < 24);
                }
            }
            index++;
        }
    }

    private LocalDate determineSemesterStartDate(Semester semester) {
        String semesterName = semester.getName();
        int year = Integer.parseInt(semesterName.replaceAll("\\D", ""));  // Tách năm từ tên học kỳ

        if (semesterName.startsWith("Spring")) {
            return LocalDate.of(year, 1, 1);  // Spring bắt đầu vào tháng 1
        } else if (semesterName.startsWith("Summer")) {
            return LocalDate.of(year, 5, 1);  // Summer bắt đầu vào tháng 5
        } else if (semesterName.startsWith("Fall")) {
            return LocalDate.of(year, 9, 1);  // Fall bắt đầu vào tháng 9
        } else {
            throw new IllegalArgumentException("Unknown semester: " + semesterName);
        }
    }

    @Transactional
    public void seedAttendanceDetail(Attendance attendance, LocalDate semesterStartDate, LocalDate semesterEndDate, Study study, Random random, String[] lecturerComments, Map<String, List<String>> lecturerCommentsHashMap, boolean withComment) {
        LocalTime baseStartTime = LocalTime.of(7, 0);
        LocalTime slotDuration = LocalTime.of(2, 15);
        LocalTime breakDuration = LocalTime.of(0, 15);

        int absentCount = study.getStatus() == StudyStatus.NOT_PASSED
                ? random.nextInt(8) + 3
                : random.nextInt(3) + 2;

        int totalSlots = attendance.getTotalSlot();
        long daysInSemester = semesterStartDate.datesUntil(semesterEndDate.plusDays(1)).count();
        long intervalDays = daysInSemester / totalSlots;  // Khoảng cách giữa các slot của môn

        Set<Integer> commentedSlots = new HashSet<>();
        while (commentedSlots.size() < 2) {
            commentedSlots.add(random.nextInt(totalSlots));
        }

        List<AttendanceDetail> detailsBatch = new ArrayList<>();

        for (int i = 0; i < totalSlots; i++) {
            LocalDate slotDate = semesterStartDate.plusDays(i * intervalDays);
            LocalDateTime slotDateTime = LocalDateTime.of(slotDate, baseStartTime);

            // Tính trạng thái của slot
            AttendanceStatus status = LocalDateTime.now().isBefore(slotDateTime)
                    ? AttendanceStatus.FUTURE
                    : i < absentCount ? AttendanceStatus.ABSENT : AttendanceStatus.PRESENT;

            String lecturerComment = commentedSlots.contains(i) ? lecturerComments[random.nextInt(lecturerComments.length)] : null;

            AttendanceDetail detail = AttendanceDetail.builder()
                    .date(slotDate)
                    .slot("Slot " + (i + 1) + "_" + baseStartTime + "-" + baseStartTime.plusHours(2).plusMinutes(15))
                    .room("Room " + (random.nextInt(10) + 1))
                    .lecturer("Lecturer " + (random.nextInt(5) + 1))
                    .groupName("Group " + (random.nextInt(5) + 1))
                    .status(status)
                    .lecturerComment((withComment && !status.equals(AttendanceStatus.ABSENT)) ? ((attendance.getSemester().getName().equals("Fall2024") || attendance.getSemester().getName().equals("Summer2024")) ? lecturerComment : null) : null)
                    .attendance(attendance)
                    .build();

            detailsBatch.add(detail);

            if (detailsBatch.size() >= BATCH_SIZE) {
                attendanceDetailRepository.saveAll(detailsBatch); // Lưu tất cả trong một batch
                attendanceDetailRepository.flush();
                detailsBatch.clear(); // Làm rỗng danh sách
            }

//            attendanceDetailRepository.save(detail);

            if(withComment && !status.equals(AttendanceStatus.ABSENT) && (lecturerComment != null && (attendance.getSemester().getName().equals("Fall2024") || attendance.getSemester().getName().equals("Summer2024")))) {
                List<String> tags = lecturerCommentsHashMap.get(lecturerComment);
                String studentCode = detail.getAttendance().getStudent().getStudentCode();
                for(String tag : tags) {
                    boolean isTagExisted = ProblemTagController.studentProblemTag.stream()
                            .filter(tagIterative -> tagIterative.getStudentCode().equals(studentCode)) // Lọc theo studentCode
                            .anyMatch(tagIterative -> tagIterative.getProblemTagName().equals(tag) && tagIterative.getSemesterName().equals(detail.getAttendance().getSemester().getName()));
                    if(true) {
                        ProblemTagController.studentProblemTag.add(DemandProblemTagDTO.builder()
                                .semesterName(detail.getAttendance().getSemester().getName())
                                .studentCode(studentCode)
                                .problemTagName(tag)
                                .source(detail.getAttendance().getSubject().getCode() + "(" + detail.getAttendance().getSubject().getName() + ")")
                                .number(1)
                                .build());
                    }
//                    else {
//                        ProblemTagController.studentProblemTag = ProblemTagController.studentProblemTag.stream()
//                                .map(tagIterative -> {
//                                    if(tagIterative.getStudentCode().equals(studentCode) && tagIterative.getProblemTagName().equals(tag) && tagIterative.getSemesterName().equals(detail.getAttendance().getSemester().getName())) {
//                                        tagIterative.setNumber(tagIterative.getNumber() + 1);
//                                        return tagIterative;
//                                    } else {
//                                        return tagIterative;
//                                    }
//                                })
//                                .collect(Collectors.toList());
//                    }
                }
            }

            // Cập nhật thời gian slot tiếp theo, đảm bảo không vượt quá 7 slot mỗi ngày
            baseStartTime = baseStartTime.plusHours(2).plusMinutes(30);
            if ((i + 1) % 7 == 0) {
                baseStartTime = LocalTime.of(7, 0);
                slotDate = slotDate.plusDays(1);
            }
        }

        if (!detailsBatch.isEmpty()) {
            attendanceDetailRepository.saveAll(detailsBatch);
            attendanceDetailRepository.flush();
            detailsBatch.clear();
        }
    }
}
