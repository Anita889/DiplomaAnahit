package com.example.diplomaanahit.mapper;

import com.example.diplomaanahit.dtos.SubjectDTO;
import com.example.diplomaanahit.entities.Subject;
import org.mapstruct.Mapper;

import java.util.Set;
import java.util.stream.Collectors;


@Mapper(componentModel = "spring")
public class SubjectMapper {

    public Set<SubjectDTO> toDTOList(Set<Subject> subjects) {
        return subjects.stream().map(this::toDTO).collect(Collectors.toSet());
    }

    private SubjectDTO toDTO(Subject subject) {
        SubjectDTO subjectDTO = new SubjectDTO();
        subjectDTO.setId(subject.getId());
        subjectDTO.setName(subject.getName());
        return subjectDTO;
    }
}
