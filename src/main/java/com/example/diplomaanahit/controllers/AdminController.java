package com.example.diplomaanahit.controllers;


import com.example.diplomaanahit.calculations.LecturerCalculationService;
import com.example.diplomaanahit.calculations.StudentTestCalculationService;
import com.example.diplomaanahit.dtos.*;
import com.example.diplomaanahit.entities.*;
import com.example.diplomaanahit.mapper.Mapper;
import com.example.diplomaanahit.services.*;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "api/user/{userId}/admins/{adminId}")
public class AdminController {

    @Autowired
    private AdminDataService adminService;

    @Autowired
    private UserDataService userDataService;

    @Autowired
    private Mapper mapper;

    @Autowired
    private FacultyDataService facultyService;

    @Autowired
    private LecturerDataService lecturerService;

    @Autowired
    private StudentGroupDataService studentGroupService;

    @Autowired
    private DepartmentDataService departmentDataService;

    @Autowired
    private StudentTestCalculationService studentTestCalculationService;

    @Autowired
    private LecturerCalculationService lecturerCalculationService;

    @Autowired
    private StudentDataService studentService;

    @Autowired
    private SubjectDataService subjectService;

    @Autowired
    private LessonDataService lessonService;

    @Autowired
    private ExamPointsService examPointsService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<?> getAdmin(@PathVariable Long userId, @PathVariable Long adminId) throws Exception {
        Admin admin = adminService.findById(adminId);
        if (admin == null) {
            throw new Exception("Admin with this id is not exist");
        }
        AdminDTO adminDTO = mapper.getAdminEntityToDTO(admin);
        return ResponseEntity.ok(adminDTO);
    }

    @RequestMapping(value = "faculties", method = RequestMethod.GET)
    public ResponseEntity<?> getFaculties(@PathVariable Long userId, @PathVariable Long adminId) throws Exception {
        Admin admin = adminService.findById(adminId);
        if (admin == null) {
            throw new Exception("Admin with this id is not exist");
        }
        List<Faculty> faculties = facultyService.findAll();
        List<FacultyDTO> facultyDTOS = mapper.getFacultyEntitiesToDTOs(faculties);
        return ResponseEntity.ok(facultyDTOS);
    }

    @RequestMapping(value = "faculties/{facultyId}/departments", method = RequestMethod.GET)
    public ResponseEntity<?> getDepartments(@PathVariable Long userId, @PathVariable Long adminId, @PathVariable Long facultyId) throws Exception {
        Admin admin = adminService.findById(adminId);
        if (admin == null) {
            throw new Exception("Admin with this id is not exist");
        }
        Faculty faculty = facultyService.findById(facultyId);
        if (faculty == null) {
            throw new Exception("Faculty with this id is not exist");
        }
        Set<Department> departments = faculty.getDepartments();
        Set<DepartmentDTO> departmentDTOS = mapper.getDepartmentEntitiesToDTOs(departments);
        return ResponseEntity.ok(departmentDTOS);
    }



    ///LECTURERS
    @RequestMapping(value = "faculties/{facultyId}/departments/{departmentId}/lecturers", method = RequestMethod.GET)
    public ResponseEntity<?> getLecturers(@PathVariable Long userId, @PathVariable Long adminId, @PathVariable Long facultyId, @PathVariable Long departmentId) throws Exception {
        Admin admin = adminService.findById(adminId);
        if (admin == null) {
            throw new Exception("Admin with this id is not exist");
        }
        Faculty faculty = facultyService.findById(facultyId);
        if (faculty == null) {
            throw new Exception("Faculty with this id is not exist");
        }
        Department department = faculty.getDepartments().stream().filter(d -> d.getId().equals(departmentId)).findFirst().orElse(null);
        if (department == null) {
            throw new Exception("Department with this id is not exist");
        }
        List<Lecturer> lecturers = lecturerService.findByDepartment(department);
        List<LecturerDTO> lecturerDTOS = mapper.getLecturerEntitiesToDTOs(lecturers);
        return ResponseEntity.ok(lecturerDTOS);
    }

    @RequestMapping(value = "faculties/{facultyId}/departments/{departmentId}/lecturers/analyze", method = RequestMethod.GET)
    public ResponseEntity<?> analyzeLecturers(@PathVariable Long userId, @PathVariable Long adminId, @PathVariable Long facultyId, @PathVariable Long departmentId) throws Exception {
        Admin admin = adminService.findById(adminId);
        if (admin == null) {
            throw new Exception("Admin with this id is not exist");
        }
        Faculty faculty = facultyService.findById(facultyId);
        if (faculty == null) {
            throw new Exception("Faculty with this id is not exist");
        }
        Department department = faculty.getDepartments().stream().filter(d -> d.getId().equals(departmentId)).findFirst().orElse(null);
        if (department == null) {
            throw new Exception("Department with this id is not exist");
        }
        List<Lecturer> lecturers = lecturerService.findByDepartment(department);
        List<LecturersAnalysisDTO> lecturersAnalysisDTOS = lecturerCalculationService.analyzeLecturers(lecturers);
        return ResponseEntity.ok(lecturersAnalysisDTOS.stream().filter(s->s.getName().equals("Ella")).collect(Collectors.toSet()));
    }

    @RequestMapping(value = "faculties/{facultyId}/departments/{departmentId}/lecturers/{lecturerId}", method = RequestMethod.GET)
    public ResponseEntity<?> getLecturer(@PathVariable Long userId, @PathVariable Long adminId, @PathVariable Long facultyId, @PathVariable Long departmentId, @PathVariable Long lecturerId) throws Exception {
        Admin admin = adminService.findById(adminId);
        if (admin == null) {
            throw new Exception("Admin with this id is not exist");
        }
        Faculty faculty = facultyService.findById(facultyId);
        if (faculty == null) {
            throw new Exception("Faculty with this id is not exist");
        }
        Department department = faculty.getDepartments().stream().filter(d -> d.getId().equals(departmentId)).findFirst().orElse(null);
        if (department == null) {
            throw new Exception("Department with this id is not exist");
        }
        Lecturer lecturer = lecturerService.findById(lecturerId);
        LecturerDTO lecturerDTO = mapper.getStudentEntityToDTO(lecturer);
        lecturerDTO.setLessons(lecturer.getLessons().stream().map(l -> {
            LessonDTO lessonDTO = new LessonDTO();
            lessonDTO.setId(l.getId());
            lessonDTO.setType(l.getType());
            lessonDTO.setIsAvailableDate(l.getAvailableDate().isBefore(LocalDate.now()));
            return lessonDTO;
        }).collect(Collectors.toSet()));
        return ResponseEntity.ok(lecturerDTO);
    }

    @RequestMapping(value = "faculties/{facultyId}/departments/{departmentId}/lecturers/add", method = RequestMethod.POST)
    public ResponseEntity<?> addLecturer(@PathVariable Long userId, @PathVariable Long adminId, @PathVariable Long facultyId, @PathVariable Long departmentId, @RequestBody LecturerDTO lecturerDTO) throws Exception {
        Admin admin = adminService.findById(adminId);
        if (admin == null) {
            throw new Exception("Admin with this id is not exist");
        }
        Faculty faculty = facultyService.findById(facultyId);
        if (faculty == null) {
            throw new Exception("Faculty with this id is not exist");
        }
        Department department = faculty.getDepartments().stream().filter(d -> d.getId().equals(departmentId)).findFirst().orElse(null);
        if (department == null) {
            throw new Exception("Department with this id is not exist");
        }
        Lecturer lecturer = new Lecturer();
        lecturer.setName(lecturerDTO.getName());
        lecturer.setEmail(lecturerDTO.getEmail());
        lecturer.setDepartment(department);
        lecturer.setRating(lecturerDTO.getRating());
        lecturer.setPassword(lecturerDTO.getPassword());
        lecturer.setSurName(lecturerDTO.getSurName());

        lecturerService.saveLecturer(lecturer);
        return ResponseEntity.ok(lecturerDTO);
    }

    @RequestMapping(value = "faculties/{facultyId}/departments/{departmentId}/lecturers/{lecturerId}/update", method = RequestMethod.PUT)
    public ResponseEntity<?> updateLecturer(@PathVariable Long userId, @PathVariable Long adminId, @PathVariable Long facultyId, @PathVariable Long departmentId, @PathVariable Long lecturerId, @RequestBody LecturerDTO lecturerDTO) throws Exception {
        Admin admin = adminService.findById(adminId);
        if (admin == null) {
            throw new Exception("Admin with this id is not exist");
        }
        Faculty faculty = facultyService.findById(facultyId);
        if (faculty == null) {
            throw new Exception("Faculty with this id is not exist");
        }
        Department department = faculty.getDepartments().stream().filter(d -> d.getId().equals(departmentId)).findFirst().orElse(null);
        if (department == null) {
            throw new Exception("Department with this id is not exist");
        }
        Lecturer lecturer = department.getLecturers().stream().filter(l -> l.getId().equals(lecturerId)).findFirst().orElse(null);
        if (lecturer == null) {
            throw new Exception("Lecturer with this id is not exist");
        }

        lecturer.setName(lecturerDTO.getName());
        lecturer.setEmail(lecturerDTO.getEmail());
        lecturer.setSurName(lecturerDTO.getSurName());
        lecturer.setPassword(lecturerDTO.getPassword());
        lecturer.setRating(lecturerDTO.getRating());
        lecturer.setDepartment(department);
        lecturerService.saveLecturer(lecturer);
        return ResponseEntity.ok(lecturerDTO);
    }

    @RequestMapping(value = "faculties/{facultyId}/departments/{departmentId}/lecturers/{lecturerId}/remove", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteLecturer(@PathVariable Long userId, @PathVariable Long adminId, @PathVariable Long facultyId, @PathVariable Long departmentId, @PathVariable Long lecturerId) throws Exception {
        Admin admin = adminService.findById(adminId);
        if (admin == null) {
            throw new Exception("Admin with this id is not exist");
        }
        Faculty faculty = facultyService.findById(facultyId);
        if (faculty == null) {
            throw new Exception("Faculty with this id is not exist");
        }
        Department department = faculty.getDepartments().stream().filter(d -> d.getId().equals(departmentId)).findFirst().orElse(null);
        if (department == null) {
            throw new Exception("Department with this id is not exist");
        }
        Lecturer lecturer = department.getLecturers().stream().filter(l -> l.getId().equals(lecturerId)).findFirst().orElse(null);
        if (lecturer == null) {
            throw new Exception("Lecturer with this id is not exist");
        }
        lecturerService.deleteLecturer(lecturer);
        return ResponseEntity.ok("Lecturer deleted");
    }



    ////STUDENT GROUPS
    @RequestMapping(value = "faculties/{facultyId}/departments/{departmentId}/studentGroups", method = RequestMethod.GET)
    public ResponseEntity<?> getStudentGroups(@PathVariable Long userId, @PathVariable Long adminId, @PathVariable Long facultyId, @PathVariable Long departmentId) throws Exception {
        Admin admin = adminService.findById(adminId);
        if (admin == null) {
            throw new Exception("Admin with this id is not exist");
        }
        Faculty faculty = facultyService.findById(facultyId);
        if (faculty == null) {
            throw new Exception("Faculty with this id is not exist");
        }
        Department department = faculty.getDepartments().stream().filter(d -> d.getId().equals(departmentId)).findFirst().orElse(null);
        if (department == null) {
            throw new Exception("Department with this id is not exist");
        }
        Set<StudentGroup> studentGroups = department.getSpecialities().stream().map(Speciality::getStudentGroups).flatMap(Set::stream).collect(Collectors.toSet());
        Set<StudentGroupDTO> studentGroupDTOS = mapper.getStudentGroupEntitiesToDTOs(studentGroups);
        return ResponseEntity.ok(studentGroupDTOS);
    }

    @RequestMapping(value = "faculties/{facultyId}/departments/{departmentId}/studentGroups/{studentGroupId}", method = RequestMethod.GET)
    public ResponseEntity<?> getStudentGroup(@PathVariable Long userId, @PathVariable Long adminId, @PathVariable Long facultyId, @PathVariable Long departmentId, @PathVariable Long studentGroupId) throws Exception {
        Admin admin = adminService.findById(adminId);
        if (admin == null) {
            throw new Exception("Admin with this id is not exist");
        }
        Faculty faculty = facultyService.findById(facultyId);
        if (faculty == null) {
            throw new Exception("Faculty with this id is not exist");
        }
        Department department = faculty.getDepartments().stream().filter(d -> d.getId().equals(departmentId)).findFirst().orElse(null);
        if (department == null) {
            throw new Exception("Department with this id is not exist");
        }
        StudentGroup studentGroup = studentGroupService.findById(studentGroupId);
        if (studentGroup == null) {
            throw new Exception("Student group with this id is not exist");
        }
        StudentGroupDTO studentGroupDTO = mapper.getStudentGroupEntityToDTO(studentGroup);
        return ResponseEntity.ok(studentGroupDTO);
    }


    @RequestMapping(value = "faculties/{facultyId}/departments/{departmentId}/studentGroups", method = RequestMethod.POST)
    public ResponseEntity<?> addStudentGroup(@PathVariable Long userId, @PathVariable Long adminId, @PathVariable Long facultyId, @PathVariable Long departmentId, @RequestBody StudentGroupDTO studentGroupDTO) throws Exception {
        Admin admin = adminService.findById(adminId);
        if (admin == null) {
            throw new Exception("Admin with this id is not exist");
        }
        Faculty faculty = facultyService.findById(facultyId);
        if (faculty == null) {
            throw new Exception("Faculty with this id is not exist");
        }
        Department department = faculty.getDepartments().stream().filter(d -> d.getId().equals(departmentId)).findFirst().orElse(null);
        if (department == null) {
            throw new Exception("Department with this id is not exist");
        }

        StudentGroup studentGroup = new StudentGroup();
        Speciality speciality = department.getSpecialities().stream().filter(s -> s.getId().equals(studentGroupDTO.getSpeciality().getId())).findFirst().orElse(null);
        studentGroup.setSpeciality(speciality);
        studentGroup.setName(studentGroupDTO.getName());
        studentGroup.setStudents(null);
        studentGroupService.saveStudentGroup(studentGroup);
        return ResponseEntity.ok(studentGroupDTO);
    }

    @RequestMapping(value = "faculties/{facultyId}/departments/{departmentId}/studentGroups/analyze", method = RequestMethod.GET)
    public ResponseEntity<?> analyzeStudentGroups(@PathVariable Long userId, @PathVariable Long adminId, @PathVariable Long facultyId, @PathVariable Long departmentId) throws Exception {
        Admin admin = adminService.findById(adminId);
        if (admin == null) {
            throw new Exception("Admin with this id is not exist");
        }
        Faculty faculty = facultyService.findById(facultyId);
        if (faculty == null) {
            throw new Exception("Faculty with this id is not exist");
        }
        Department department = faculty.getDepartments().stream().filter(d -> d.getId().equals(departmentId)).findFirst().orElse(null);
        if (department == null) {
            throw new Exception("Department with this id is not exist");
        }
        List<StudentGroup> studentGroups = studentGroupService.findAllByDepartment(department);
        List<Lesson> lessons = lessonService.findAllByStudentGroups(studentGroups);
        List<StudentGroupsAnalysisDTO> analyzedData = studentTestCalculationService.analyzeStudentGroups(studentGroups, null, null, null);
        return ResponseEntity.ok(analyzedData);
    }

    @RequestMapping(value = "faculties/{facultyId}/departments/{departmentId}/studentGroups/{studentGroupId}/update", method = RequestMethod.PUT)
    public ResponseEntity<?> updateStudentGroup(@PathVariable Long userId, @PathVariable Long adminId, @PathVariable Long facultyId, @PathVariable Long departmentId, @PathVariable Long studentGroupId, @RequestBody StudentGroupDTO studentGroupDTO) throws Exception {
        Admin admin = adminService.findById(adminId);
        if (admin == null) {
            throw new Exception("Admin with this id is not exist");
        }
        Faculty faculty = facultyService.findById(facultyId);
        if (faculty == null) {
            throw new Exception("Faculty with this id is not exist");
        }
        Department department = departmentDataService.findById(departmentId);
        if (department == null) {
            throw new Exception("Department with this id is not exist");
        }
        StudentGroup studentGroup = studentGroupService.findById(studentGroupId);
        if (studentGroup == null) {
            throw new Exception("Student group with this id is not exist");
        }
        studentGroup.setName(studentGroupDTO.getName());
        studentGroupService.saveStudentGroup(studentGroup);
        return ResponseEntity.ok(studentGroupDTO);
    }

    @RequestMapping(value = "faculties/{facultyId}/departments/{departmentId}/studentGroups/{studentGroupId}/remove", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteStudentGroup(@PathVariable Long userId, @PathVariable Long adminId, @PathVariable Long facultyId, @PathVariable Long departmentId, @PathVariable Long studentGroupId) throws Exception {
        Admin admin = adminService.findById(adminId);
        if (admin == null) {
            throw new Exception("Admin with this id is not exist");
        }
        Faculty faculty = facultyService.findById(facultyId);
        if (faculty == null) {
            throw new Exception("Faculty with this id is not exist");
        }
        Department department = departmentDataService.findById(departmentId);
        if (department == null) {
            throw new Exception("Department with this id is not exist");
        }
        StudentGroup studentGroup = studentGroupService.findById(studentGroupId);
        if (studentGroup == null) {
            throw new Exception("Student group with this id is not exist");
        }
        studentGroupService.deleteStudentGroup(studentGroup);
        return ResponseEntity.ok("Student group deleted");
    }




    ///// STUDENT

    @RequestMapping(value = "faculties/{facultyId}/departments/{departmentId}/studentGroups/{studentGroupId}/getStudents", method = RequestMethod.GET)
    public ResponseEntity<?> getStudentGroupStudents(@PathVariable Long userId, @PathVariable Long adminId, @PathVariable Long facultyId, @PathVariable Long departmentId, @PathVariable Long studentGroupId) throws Exception {
        Admin admin = adminService.findById(adminId);
        if (admin == null) {
            throw new Exception("Admin with this id is not exist");
        }
        Faculty faculty = facultyService.findById(facultyId);
        if (faculty == null) {
            throw new Exception("Faculty with this id is not exist");
        }
        Department department = faculty.getDepartments().stream().filter(d -> d.getId().equals(departmentId)).findFirst().orElse(null);
        if (department == null) {
            throw new Exception("Department with this id is not exist");
        }
        StudentGroup studentGroup = studentGroupService.findById(studentGroupId);
        if (studentGroup == null) {
            throw new Exception("Student group with this id is not exist");
        }
        List<Student> students = studentGroup.getStudents().stream().collect(Collectors.toList());
        List<StudentDTO> studentDTOS = students.stream().map(s -> mapper.getStudentEntityToDTO(s)).collect(Collectors.toList());
        return ResponseEntity.ok(studentDTOS);
    }

    @RequestMapping(value = "faculties/{facultyId}/departments/{departmentId}/studentGroups/{studentGroupId}/getStudents/{studentId}", method = RequestMethod.GET)
    public ResponseEntity<StudentDTO> getStudent(@PathVariable Long userId, @PathVariable Long adminId, @PathVariable Long facultyId, @PathVariable Long departmentId, @PathVariable Long studentGroupId, @PathVariable Long studentId) {

        // Call service method to fetch student data
        Student student = studentService.findById(studentId);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(mapper.getStudentEntityToDTO(student));
    }

    @RequestMapping(value = "faculties/{facultyId}/departments/{departmentId}/studentGroups/{studentGroupId}/getStudents/add", method = RequestMethod.POST)
    public ResponseEntity<?> addStudent(@PathVariable Long userId, @PathVariable Long adminId, @PathVariable Long facultyId, @PathVariable Long departmentId, @PathVariable Long studentGroupId, @RequestBody StudentDTO studentDTO) throws Exception {
        Admin admin = adminService.findById(adminId);
        if (admin == null) {
            throw new Exception("Admin with this id is not exist");
        }
        Faculty faculty = facultyService.findById(facultyId);
        if (faculty == null) {
            throw new Exception("Faculty with this id is not exist");
        }
        Department department = departmentDataService.findById(departmentId);
        if (department == null) {
            throw new Exception("Department with this id is not exist");
        }
        StudentGroup studentGroup = studentGroupService.findById(studentGroupId);
        if (studentGroup == null) {
            throw new Exception("Student group with this id is not exist");
        }
        Student student = new Student();
        student.setName(studentDTO.getStudentName());
        student.setSurname(studentDTO.getStudentSurname());
        student.setEmail(studentDTO.getEmail());
        student.setPassword(studentDTO.getPassword());
        student.setStudentGroup(studentGroup);
        student.setMog(studentDTO.getMog());
        student.setStudentGroup(studentGroup);
        studentService.save(student);
        return ResponseEntity.ok(studentDTO);
    }

    @RequestMapping(value = "faculties/{facultyId}/departments/{departmentId}/studentGroups/{studentGroupId}/getStudents/{studentId}/update", method = RequestMethod.PUT)
    public ResponseEntity<StudentDTO> updateStudent(@PathVariable Long userId, @PathVariable Long adminId, @PathVariable Long facultyId, @PathVariable Long departmentId, @PathVariable Long studentGroupId, @PathVariable Long studentId, @RequestBody StudentDTO updatedStudent) {
        Student student = studentService.findById(studentId);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        student.setStudentGroup(studentGroupService.findById(updatedStudent.getStudentGroupDTO().getId()));
        studentService.save(student);
        // Call service method to update student data
        return ResponseEntity.ok(mapper.getStudentEntityToDTO(student));
    }

    @RequestMapping(value = "faculties/{facultyId}/departments/{departmentId}/studentGroups/{studentGroupId}/getStudents/{studentId}/remove", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteStudent(@PathVariable Long userId, @PathVariable Long adminId, @PathVariable Long facultyId, @PathVariable Long departmentId, @PathVariable Long studentGroupId, @PathVariable Long studentId) {
        Optional<Student> studentOpt = Optional.ofNullable(studentService.findById(studentId));

        if (studentOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        studentService.remove(studentOpt.get());
        return ResponseEntity.ok("Student removed successfully.");
    }


    ////Lesson
    @RequestMapping(value = "faculties/{facultyId}/departments/{departmentId}/lecturers/addLesson", method = RequestMethod.POST)
    public ResponseEntity<?> addLesson(@PathVariable Long userId, @PathVariable Long adminId, @PathVariable Long facultyId, @PathVariable Long departmentId, @RequestBody LessonCreateDTO lessonCreationDTO) throws Exception {
        Admin admin = adminService.findById(adminId);
        if (admin == null) {
            throw new Exception("Admin with this id is not exist");
        }
        Faculty faculty = facultyService.findById(facultyId);
        if (faculty == null) {
            throw new Exception("Faculty with this id is not exist");
        }
        Department department = departmentDataService.findById(departmentId);
        if (department == null) {
            throw new Exception("Department with this id is not exist");
        }
        Lecturer lecturer = lecturerService.findById(lessonCreationDTO.getLecturerId());
        if (lecturer == null) {
            throw new Exception("Lecturer with this id is not exist");
        }

        Lesson lesson = new Lesson();
        lesson.setType(lessonCreationDTO.getLessonName());
        lesson.setAvailableDate(lessonCreationDTO.getAvailabilityDate());
        lesson.setSubject(subjectService.findById(lessonCreationDTO.getSubjectDTO().getId()));
        lesson.setStudentGroup(studentGroupService.findById(lessonCreationDTO.getStudentGroupId()));
        lesson.setLecturer(lecturer);
        lessonService.save(lesson);
        return ResponseEntity.ok(lessonCreationDTO);
    }

    ////Calculative and other methods
    @RequestMapping(value = "departments/{departmentId}/specialities", method = RequestMethod.GET)
    public ResponseEntity<?> getSpecialities(@PathVariable Long userId, @PathVariable Long adminId, @PathVariable Long departmentId) throws Exception {
        Admin admin = adminService.findById(adminId);
        if (admin == null) {
            throw new Exception("Admin with this id is not exist");
        }
        Department department = departmentDataService.findById(departmentId);
        if (department == null) {
            throw new Exception("Department with this id is not exist");
        }
        Set<Speciality> specialities = department.getSpecialities();
        Set<SpecialityDTO> specialityDTOS = specialities.stream().map(s -> mapper.getSpecialityEntityToDTO(s)).collect(Collectors.toSet());
        return ResponseEntity.ok(specialityDTOS);
    }

    @RequestMapping(value = "faculties/{facultyId}/departments/{departmentId}/subjects", method = RequestMethod.GET)
    public ResponseEntity<?> getSubjects(@PathVariable Long userId, @PathVariable Long adminId, @PathVariable Long departmentId) throws Exception{
        Admin admin = adminService.findById(adminId);
        if (admin == null) {
            throw new Exception("Admin with this id is not exist");
        }
        Department department = departmentDataService.findById(departmentId);
        if (department == null) {
            throw new Exception("Department with this id is not exist");
        }

        Set<Subject> subjects = subjectService.findAll();
        return ResponseEntity.ok(subjects);
    }


    @RequestMapping(value = "faculties/{facultyId}/departments/{departmentId}/studentGroups", method = RequestMethod.PUT)
    public ResponseEntity<?> getDepartmentStudentGroups(@PathVariable Long userId, @PathVariable Long adminId, @PathVariable Long facultyId, @PathVariable Long departmentId) throws Exception {
        Admin admin = adminService.findById(adminId);
        if (admin == null) {
            throw new Exception("Admin with this id is not exist");
        }
        Faculty faculty = facultyService.findById(facultyId);
        if (faculty == null) {
            throw new Exception("Faculty with this id is not exist");
        }
        Department department = departmentDataService.findById(departmentId);
        if (department == null) {
            throw new Exception("Department with this id is not exist");
        }

        List<StudentGroup> list = department.getSpecialities()
                .stream()
                .map(Speciality::getStudentGroups)
                .flatMap(Set::stream)
                .collect(Collectors.toList());

        return ResponseEntity.ok(list.stream().map(s -> mapper.getStudentGroupEntityToDTO(s)).collect(Collectors.toList()));
    }

    @RequestMapping(value = "faculties/{facultyId}/departments/{departmentId}/studentGroups/{studentGroupId}/calculate", method = RequestMethod.PUT)
    public ResponseEntity<?> calculateStudentGroup(@PathVariable Long userId, @PathVariable Long adminId, @PathVariable Long facultyId, @PathVariable Long departmentId, @PathVariable Long studentGroupId) throws Exception {
        Admin admin = adminService.findById(adminId);
        if (admin == null) {
            throw new Exception("Admin with this id is not exist");
        }
        Faculty faculty = facultyService.findById(facultyId);
        if (faculty == null) {
            throw new Exception("Faculty with this id is not exist");
        }
        Department department = departmentDataService.findById(departmentId);
        if (department == null) {
            throw new Exception("Department with this id is not exist");
        }
        StudentGroup studentGroup = studentGroupService.findById(studentGroupId);
        if (studentGroup == null) {
            throw new Exception("Student group with this id is not exist");
        }
        Map<String, List<StudentDTO>> d = studentTestCalculationService.analyzeStudentGroupByMOG(studentGroup);
        return ResponseEntity.ok(d);
    }

    @RequestMapping(value = "faculties/{facultyId}/departments/{departmentId}/studentGroups/calculate", method = RequestMethod.PUT)
    public ResponseEntity<?> calculateDepartmentLessons(@PathVariable Long userId, @PathVariable Long adminId, @PathVariable Long facultyId, @PathVariable Long departmentId) throws Exception {
        Admin admin = adminService.findById(adminId);
        if (admin == null) {
            throw new Exception("Admin with this id is not exist");
        }
        Faculty faculty = facultyService.findById(facultyId);
        if (faculty == null) {
            throw new Exception("Faculty with this id is not exist");
        }
        Department department = departmentDataService.findById(departmentId);
        if (department == null) {
            throw new Exception("Department with this id is not exist");
        }

        List<StudentGroup> list = department.getSpecialities()
                .stream()
                .map(Speciality::getStudentGroups)
                .flatMap(Set::stream)
                .collect(Collectors.toList());

        Map<String, Map<String,List<StudentDTO>>> d = studentTestCalculationService.analyzeStudentGroupsByDepartmentByMOG(list);

        return ResponseEntity.ok(d);
    }

}
