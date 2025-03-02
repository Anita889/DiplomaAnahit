package com.example.diplomaanahit.mapper;

import com.example.diplomaanahit.dtos.AdminDTO;
import com.example.diplomaanahit.dtos.DepartmentDTO;
import com.example.diplomaanahit.dtos.FacultyDTO;
import com.example.diplomaanahit.dtos.LecturerDTO;
import com.example.diplomaanahit.dtos.QuestionVariantsLecturerDTO;
import com.example.diplomaanahit.dtos.QuestionVariantsStudentDTO;
import com.example.diplomaanahit.dtos.SpecialityDTO;
import com.example.diplomaanahit.dtos.StudentDTO;
import com.example.diplomaanahit.dtos.StudentGroupDTO;
import com.example.diplomaanahit.dtos.SubjectDTO;
import com.example.diplomaanahit.entities.Admin;
import com.example.diplomaanahit.entities.Department;
import com.example.diplomaanahit.entities.Faculty;
import com.example.diplomaanahit.entities.Lecturer;
import com.example.diplomaanahit.entities.Lesson;
import com.example.diplomaanahit.entities.QuestionVariantsEntity;
import com.example.diplomaanahit.entities.Speciality;
import com.example.diplomaanahit.entities.Student;
import com.example.diplomaanahit.entities.StudentGroup;
import com.example.diplomaanahit.entities.Subject;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Component
public class Mapper {
    public LecturerDTO getLecturerEntityToDTO(Lecturer entity) {
        LecturerDTO dto = new LecturerDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSurName(entity.getSurName());
        dto.setEmail(entity.getEmail());
        dto.setPassword(entity.getPassword());
        return dto;
    }


    public List<QuestionVariantsStudentDTO> getQuestionVariantsDTOList(List<QuestionVariantsEntity> entities, Boolean available){
        List<QuestionVariantsStudentDTO> dtos = new ArrayList<>();
        for (QuestionVariantsEntity entity : entities) {
            QuestionVariantsStudentDTO dto = getQuestionVariantsDTO(entity, available);
            dtos.add(dto);
        }
        return  dtos;
    }

    public QuestionVariantsStudentDTO getQuestionVariantsDTO(QuestionVariantsEntity entity, Boolean available) {
        QuestionVariantsStudentDTO dto = new QuestionVariantsStudentDTO();
        dto.setId(entity.getId());
        dto.setQuestion(entity.getQuestion());
        dto.setVariant1(entity.getVariant1());
        dto.setVariant2(entity.getVariant2());
        dto.setVariant3(entity.getVariant3());
        return dto;
    }

    public List<QuestionVariantsEntity> getQuestionVariantsEntityList(List<QuestionVariantsLecturerDTO> questions, Lesson lesson) {
        List<QuestionVariantsEntity> entities = new ArrayList<>();
        for (QuestionVariantsLecturerDTO dto : questions) {
            QuestionVariantsEntity entity = getQuestionVariantsEntity(dto, lesson);
            entities.add(entity);
        }
        return entities;
    }

    public QuestionVariantsEntity getQuestionVariantsEntity(QuestionVariantsLecturerDTO dto, Lesson lesson) {
        QuestionVariantsEntity entity = new QuestionVariantsEntity();
        entity.setLesson(lesson);
        entity.setQuestion(dto.getQuestion());
        entity.setVariant1(dto.getVariant1());
        entity.setVariant2(dto.getVariant2());
        entity.setVariant3(dto.getVariant3());
        if(dto.getCorrectAnswer().equals(dto.getVariant1())){
            entity.setNumber(1);
        }
        else if(dto.getCorrectAnswer().equals(dto.getVariant2())){
            entity.setNumber(2);
        }
        else if(dto.getCorrectAnswer().equals(dto.getVariant3())){
            entity.setNumber(3);
        }
        return entity;
    }

    public StudentDTO getLecturerEntityToDTO(Student entity){
        StudentDTO dto = new StudentDTO();
        dto.setId(entity.getId());
        dto.setStudentName(entity.getName());
        dto.setEmail(entity.getEmail());
        dto.setStudentSurname(entity.getSurname());
        dto.setAcademyGroupId(entity.getStudentGroup().getId());
        return dto;
    }

    public Set<SubjectDTO> getStudentEntitiesToDTOs(Set<Subject> subjects) {
        return subjects.stream().map(this::getLecturerEntityToDTO).collect(Collectors.toSet());
    }

    public SubjectDTO getLecturerEntityToDTO(Subject subject) {
        SubjectDTO subjectDTO = new SubjectDTO();
        subjectDTO.setId(subject.getId());
        subjectDTO.setName(subject.getName());
        return subjectDTO;
    }

    public AdminDTO getAdminEntityToDTO(Admin admin) {
        AdminDTO dto = new AdminDTO();
        dto.setId(admin.getId());
        dto.setName(admin.getName());
        dto.setSurname(admin.getSurname());
        dto.setEmail(admin.getEmail());
        dto.setPassword(admin.getPassword());
        return dto;
    }

    public List<FacultyDTO> getFacultyEntitiesToDTOs(List<Faculty> faculties) {
        return faculties.stream().map(this::getFacultyEntityToDTO).collect(Collectors.toList());
    }

    public FacultyDTO getFacultyEntityToDTO(Faculty faculty) {
        FacultyDTO facultyDTO = new FacultyDTO();
        facultyDTO.setId(faculty.getId());
        facultyDTO.setName(faculty.getName());
        return facultyDTO;
    }

    public Set<DepartmentDTO> getDepartmentEntitiesToDTOs(Set<Department> departments) {
        return departments.stream().map(this::getDepartmentEntityToDTO).collect(Collectors.toSet());
    }

    public DepartmentDTO getDepartmentEntityToDTO(Department department) {
        DepartmentDTO departmentDTO = new DepartmentDTO();
        departmentDTO.setId(department.getId());
        departmentDTO.setName(department.getName());
        return departmentDTO;
    }

    public List<LecturerDTO> getLecturerEntitiesToDTOs(List<Lecturer> lecturers) {
        return lecturers.stream().map(this::getLecturerEntityToDTO).collect(Collectors.toList());
    }

    public Set<StudentGroupDTO> getStudentGroupEntitiesToDTOs(Set<StudentGroup> studentGroups) {
        return studentGroups.stream().map(this::getStudentGroupEntityToDTO).collect(Collectors.toSet());
    }

    public StudentGroupDTO getStudentGroupEntityToDTO(StudentGroup studentGroup) {
        StudentGroupDTO studentGroupDTO = new StudentGroupDTO();
        studentGroupDTO.setId(studentGroup.getId());
        studentGroupDTO.setName(studentGroup.getName());
        studentGroupDTO.setSpeciality(getSpecialityEntityToDTO(studentGroup.getSpeciality()));
        return studentGroupDTO;
    }

    public SpecialityDTO getSpecialityEntityToDTO(Speciality speciality) {
        SpecialityDTO specialityDTO = new SpecialityDTO();
        specialityDTO.setId(speciality.getId());
        specialityDTO.setName(speciality.getName());
        specialityDTO.setDepartment(getDepartmentEntityToDTO(speciality.getDepartment()));
        return specialityDTO;
    }

    public StudentGroup getStudentGroupDTOToEntity(StudentGroupDTO studentGroupDTO) {
        StudentGroup studentGroup = new StudentGroup();
        studentGroup.setName(studentGroupDTO.getName());
        studentGroup.setSpeciality(getSpecialityDTOToEntity(studentGroupDTO.getSpeciality()));
        return studentGroup;
    }

    private Speciality getSpecialityDTOToEntity(SpecialityDTO speciality) {
        Speciality specialityEntity = new Speciality();
        specialityEntity.setName(speciality.getName());
        specialityEntity.setDepartment(getDepartmentDTOToEntity(speciality.getDepartment()));
        return specialityEntity;
    }

    private Department getDepartmentDTOToEntity(DepartmentDTO department) {
        Department departmentEntity = new Department();
        departmentEntity.setName(department.getName());
        return departmentEntity;
    }

    public List<QuestionVariantsLecturerDTO> getQuestionVariantsLecturerDTOList(List<QuestionVariantsEntity> list) {
        List<QuestionVariantsLecturerDTO> dtos = new ArrayList<>();
        for (QuestionVariantsEntity entity : list) {
            QuestionVariantsLecturerDTO dto = getQuestionVariantsLecturerDTO(entity);
            dtos.add(dto);
        }
        return dtos;
    }

    public QuestionVariantsLecturerDTO getQuestionVariantsLecturerDTO(QuestionVariantsEntity entity) {
        QuestionVariantsLecturerDTO dto = new QuestionVariantsLecturerDTO();
        dto.setId(entity.getId());
        dto.setQuestion(entity.getQuestion());
        dto.setVariant1(entity.getVariant1());
        dto.setVariant2(entity.getVariant2());
        dto.setVariant3(entity.getVariant3());
        if(entity.getNumber() == 1){
            dto.setCorrectAnswer(entity.getVariant1());
        }
        else if(entity.getNumber() == 2){
            dto.setCorrectAnswer(entity.getVariant2());
        }
        else if(entity.getNumber() == 3){
            dto.setCorrectAnswer(entity.getVariant3());
        }
        return dto;
    }
}
