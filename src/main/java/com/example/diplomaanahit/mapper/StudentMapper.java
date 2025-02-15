package com.example.diplomaanahit.mapper;

import com.example.diplomaanahit.dtos.StudentDTO;
import com.example.diplomaanahit.entities.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Component
public class StudentMapper {


    public StudentDTO toDTO(Student entity){
        StudentDTO dto = new StudentDTO();
        dto.setId(entity.getId());
        dto.setStudentName(entity.getName());
        dto.setEmail(entity.getEmail());
        return dto;
    }

}
