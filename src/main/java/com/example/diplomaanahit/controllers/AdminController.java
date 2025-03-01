package com.example.diplomaanahit.controllers;


import com.example.diplomaanahit.calculations.LecturerCalculationService;
import com.example.diplomaanahit.calculations.StudentTestCalculationService;
import com.example.diplomaanahit.dtos.AdminDTO;
import com.example.diplomaanahit.dtos.DepartmentDTO;
import com.example.diplomaanahit.dtos.FacultyDTO;
import com.example.diplomaanahit.dtos.LecturerDTO;
import com.example.diplomaanahit.dtos.StudentGroupDTO;
import com.example.diplomaanahit.entities.Admin;
import com.example.diplomaanahit.entities.Department;
import com.example.diplomaanahit.entities.Faculty;
import com.example.diplomaanahit.entities.Lecturer;
import com.example.diplomaanahit.entities.Lesson;
import com.example.diplomaanahit.entities.Speciality;
import com.example.diplomaanahit.entities.StudentGroup;
import com.example.diplomaanahit.mapper.Mapper;
import com.example.diplomaanahit.services.AdminDataService;
import com.example.diplomaanahit.services.DepartmentDataService;
import com.example.diplomaanahit.services.FacultyDataService;
import com.example.diplomaanahit.services.LecturerDataService;
import com.example.diplomaanahit.services.StudentGroupDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "api/admins/{adminId}")
public class AdminController {

    @Autowired
    private AdminDataService adminService;

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

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<?> getAdmin(@PathVariable Long adminId) throws Exception {
        Admin admin = adminService.findById(adminId);
        if(admin == null){
            throw new Exception("Admin with this id is not exist");
        }
        AdminDTO adminDTO = mapper.getAdminEntityToDTO(admin);
        return ResponseEntity.ok(adminDTO);
    }

    @RequestMapping(value = "faculties", method = RequestMethod.GET)
    public ResponseEntity<?> getFaculties(@PathVariable Long adminId) throws Exception {
        Admin admin = adminService.findById(adminId);
        if(admin == null){
            throw new Exception("Admin with this id is not exist");
        }
        List<Faculty>  faculties = facultyService.findAll();
        List<FacultyDTO> facultyDTOS = mapper.getFacultyEntitiesToDTOs(faculties);
        return ResponseEntity.ok(facultyDTOS);
    }

    @RequestMapping(value = "faculties/{facultyId}/departments", method = RequestMethod.GET)
    public ResponseEntity<?> getDepartments(@PathVariable Long adminId, @PathVariable Long facultyId) throws Exception {
        Admin admin = adminService.findById(adminId);
        if(admin == null){
            throw new Exception("Admin with this id is not exist");
        }
        Faculty faculty = facultyService.findById(facultyId);
        if(faculty == null){
            throw new Exception("Faculty with this id is not exist");
        }
        Set<Department> departments = faculty.getDepartments();
        Set<DepartmentDTO> departmentDTOS = mapper.getDepartmentEntitiesToDTOs(departments);
        return ResponseEntity.ok(departmentDTOS);
    }

    @RequestMapping(value = "faculties/{facultyId}/departments/{departmentId}/lecturers", method = RequestMethod.GET)
    public ResponseEntity<?> getLecturers(@PathVariable Long adminId, @PathVariable Long facultyId, @PathVariable Long departmentId) throws Exception {
        Admin admin = adminService.findById(adminId);
        if(admin == null){
            throw new Exception("Admin with this id is not exist");
        }
        Faculty faculty = facultyService.findById(facultyId);
        if(faculty == null){
            throw new Exception("Faculty with this id is not exist");
        }
        Department department = faculty.getDepartments().stream().filter(d -> d.getId().equals(departmentId)).findFirst().orElse(null);
        if(department == null){
            throw new Exception("Department with this id is not exist");
        }
        Set<Lecturer> lecturers = department.getLecturers();
        Set<LecturerDTO> lecturerDTOS = mapper.getLecturerEntitiesToDTOs(lecturers);
        return ResponseEntity.ok(lecturerDTOS);
    }

    @RequestMapping(value = "faculties/{facultyId}/departments/{departmentId}/lecturers", method = RequestMethod.POST)
    public ResponseEntity<?> addLecturer(@PathVariable Long adminId, @PathVariable Long facultyId, @PathVariable Long departmentId, LecturerDTO lecturerDTO) throws Exception {
        Admin admin = adminService.findById(adminId);
        if(admin == null){
            throw new Exception("Admin with this id is not exist");
        }
        Faculty faculty = facultyService.findById(facultyId);
        if(faculty == null){
            throw new Exception("Faculty with this id is not exist");
        }
        Department department = faculty.getDepartments().stream().filter(d -> d.getId().equals(departmentId)).findFirst().orElse(null);
        if(department == null){
            throw new Exception("Department with this id is not exist");
        }
        Lecturer lecturer = new Lecturer();
        lecturer.setName(lecturerDTO.getName());
        lecturer.setEmail(lecturerDTO.getEmail());
        lecturer.setDepartment(department);
        lecturerService.saveLecturer(lecturer);
        return ResponseEntity.ok(lecturerDTO);
    }

    @RequestMapping(value = "faculties/{facultyId}/departments/{departmentId}/lecturers/{lecturerId}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateLecturer(@PathVariable Long adminId, @PathVariable Long facultyId, @PathVariable Long departmentId, @PathVariable Long lecturerId, LecturerDTO lecturerDTO) throws Exception {
        Admin admin = adminService.findById(adminId);
        if(admin == null){
            throw new Exception("Admin with this id is not exist");
        }
        Faculty faculty = facultyService.findById(facultyId);
        if(faculty == null){
            throw new Exception("Faculty with this id is not exist");
        }
        Department department = faculty.getDepartments().stream().filter(d -> d.getId().equals(departmentId)).findFirst().orElse(null);
        if(department == null){
            throw new Exception("Department with this id is not exist");
        }
        Lecturer lecturer = department.getLecturers().stream().filter(l -> l.getId().equals(lecturerId)).findFirst().orElse(null);
        if(lecturer == null){
            throw new Exception("Lecturer with this id is not exist");
        }

        lecturer.setName(lecturerDTO.getName());
        lecturer.setEmail(lecturerDTO.getEmail());
        lecturer.setSurName(lecturerDTO.getSurName());
        lecturer.setLessons(lecturerDTO.getLessons().stream().map(l -> {
            Lesson lesson = new Lesson();
            lesson.setAvailableDate(l.getAvailableDate());
            lesson.setSubject(l.getSubject());
            lesson.setType(l.getType());
            return lesson;
        }).collect(Collectors.toSet()));
        lecturerService.saveLecturer(lecturer);
        return ResponseEntity.ok(lecturerDTO);
    }

    @RequestMapping(value = "faculties/{facultyId}/departments/{departmentId}/lecturers/{lecturerId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteLecturer(@PathVariable Long adminId, @PathVariable Long facultyId, @PathVariable Long departmentId, @PathVariable Long lecturerId) throws Exception {
        Admin admin = adminService.findById(adminId);
        if(admin == null){
            throw new Exception("Admin with this id is not exist");
        }
        Faculty faculty = facultyService.findById(facultyId);
        if(faculty == null){
            throw new Exception("Faculty with this id is not exist");
        }
        Department department = faculty.getDepartments().stream().filter(d -> d.getId().equals(departmentId)).findFirst().orElse(null);
        if(department == null){
            throw new Exception("Department with this id is not exist");
        }
        Lecturer lecturer = department.getLecturers().stream().filter(l -> l.getId().equals(lecturerId)).findFirst().orElse(null);
        if(lecturer == null){
            throw new Exception("Lecturer with this id is not exist");
        }
        lecturerService.deleteLecturer(lecturer);
        return ResponseEntity.ok("Lecturer deleted");
    }

    @RequestMapping(value = "faculties/{facultyId}/departments/{departmentId}/studentGroups", method = RequestMethod.GET)
    public ResponseEntity<?> getStudentGroups(@PathVariable Long adminId, @PathVariable Long facultyId, @PathVariable Long departmentId) throws Exception {
        Admin admin = adminService.findById(adminId);
        if(admin == null){
            throw new Exception("Admin with this id is not exist");
        }
        Faculty faculty = facultyService.findById(facultyId);
        if(faculty == null){
            throw new Exception("Faculty with this id is not exist");
        }
        Department department = faculty.getDepartments().stream().filter(d -> d.getId().equals(departmentId)).findFirst().orElse(null);
        if(department == null){
            throw new Exception("Department with this id is not exist");
        }
        Set<StudentGroup> studentGroups = department.getSpecialities().stream().map(Speciality::getStudentGroups).flatMap(Set::stream).collect(Collectors.toSet());
        Set<StudentGroupDTO> studentGroupDTOS = mapper.getStudentGroupEntitiesToDTOs(studentGroups);
        return ResponseEntity.ok(studentGroupDTOS);
    }

    @RequestMapping(value = "faculties/{facultyId}/departments/{departmentId}/studentGroups", method = RequestMethod.POST)
    public ResponseEntity<?> addStudentGroup(@PathVariable Long adminId, @PathVariable Long facultyId, @PathVariable Long departmentId, StudentGroupDTO studentGroupDTO) throws Exception {
        Admin admin = adminService.findById(adminId);
        if(admin == null){
            throw new Exception("Admin with this id is not exist");
        }
        Faculty faculty = facultyService.findById(facultyId);
        if(faculty == null){
            throw new Exception("Faculty with this id is not exist");
        }
        Department department = faculty.getDepartments().stream().filter(d -> d.getId().equals(departmentId)).findFirst().orElse(null);
        if(department == null){
            throw new Exception("Department with this id is not exist");
        }
        StudentGroup studentGroup = mapper.getStudentGroupDTOToEntity(studentGroupDTO);
        studentGroupService.saveStudentGroup(studentGroup);
        return ResponseEntity.ok(studentGroupDTO);
    }

    @RequestMapping(value = "faculties/{facultyId}/departments/{departmentId}/studentGroups/{studentGroupId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteStudentGroup(@PathVariable Long adminId, @PathVariable Long facultyId, @PathVariable Long departmentId, Long studentGroupId) throws Exception {
        Admin admin = adminService.findById(adminId);
        if(admin == null){
            throw new Exception("Admin with this id is not exist");
        }
        Faculty faculty = facultyService.findById(facultyId);
        if(faculty == null){
            throw new Exception("Faculty with this id is not exist");
        }
        Department department = departmentDataService.findById(departmentId);
        if(department == null){
            throw new Exception("Department with this id is not exist");
        }
        StudentGroup studentGroup = studentGroupService.findById(studentGroupId);
        if(studentGroup == null){
            throw new Exception("Student group with this id is not exist");
        }
        studentGroupService.deleteStudentGroup(studentGroup);
        return ResponseEntity.ok("Student group deleted");
    }

    @RequestMapping(value = "faculties/{facultyId}/departments/{departmentId}/studentGroups/{studentGroupId}/calculate", method = RequestMethod.PUT)
    public ResponseEntity<?> calculateStudentGroup(@PathVariable Long adminId, @PathVariable Long facultyId, @PathVariable Long departmentId, @PathVariable Long studentGroupId) throws Exception {
        Admin admin = adminService.findById(adminId);
        if(admin == null){
            throw new Exception("Admin with this id is not exist");
        }
        Faculty faculty = facultyService.findById(facultyId);
        if(faculty == null){
            throw new Exception("Faculty with this id is not exist");
        }
        Department department = departmentDataService.findById(departmentId);
        if(department == null){
            throw new Exception("Department with this id is not exist");
        }
        StudentGroup studentGroup = studentGroupService.findById(studentGroupId);
        if(studentGroup == null){
            throw new Exception("Student group with this id is not exist");
        }
        Double d = studentTestCalculationService.calculateStudentGroup(studentGroup);
        return ResponseEntity.ok(d);
    }

    @RequestMapping(value = "faculties/{facultyId}/departments/{departmentId}/calculate", method = RequestMethod.PUT)
    public ResponseEntity<?> calculateDepartmentLecturers(@PathVariable Long adminId, @PathVariable Long facultyId, @PathVariable Long departmentId) throws Exception {
        Admin admin = adminService.findById(adminId);
        if(admin == null){
            throw new Exception("Admin with this id is not exist");
        }
        Faculty faculty = facultyService.findById(facultyId);
        if(faculty == null){
            throw new Exception("Faculty with this id is not exist");
        }
        Department department = departmentDataService.findById(departmentId);
        if(department == null){
            throw new Exception("Department with this id is not exist");
        }
        Set<Lecturer> lecturers = department.getLecturers();
        if(lecturers == null){
            throw new Exception("Lecturer with this id is not exist");
        }
        Double d = lecturerCalculationService.calculateLecturers(lecturers);
        return ResponseEntity.ok(d);
    }

}
