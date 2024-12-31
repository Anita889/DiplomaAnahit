package com.example.diplomaanahit.mapper;

import com.example.diplomaanahit.dtos.StudentDTO;
import com.example.diplomaanahit.entities.StudentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface StudentMapper {
    StudentMapper INSTANCE = Mappers.getMapper(StudentMapper.class);

    @Mapping(source = "academyGroup.id", target = "academyGroupId")
    StudentDTO toDTO(StudentEntity entity);

    @Mapping(source = "academyGroupId", target = "academyGroup.id")
    StudentEntity toEntity(StudentDTO dto);
}
