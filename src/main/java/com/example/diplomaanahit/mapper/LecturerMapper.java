package com.example.diplomaanahit.mapper;

import com.example.diplomaanahit.dtos.LecturerDTO;
import com.example.diplomaanahit.entities.Lecturer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface LecturerMapper {
    LecturerMapper INSTANCE = Mappers.getMapper(LecturerMapper.class);

     LecturerDTO toDTO(Lecturer entity);

    Lecturer toEntity(LecturerDTO dto);
}
