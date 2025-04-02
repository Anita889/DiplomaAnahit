package com.example.diplomaanahit.services;


import com.example.diplomaanahit.dtos.SubjectDTO;
import com.example.diplomaanahit.entities.Lesson;
import com.example.diplomaanahit.entities.StudentGroup;
import com.example.diplomaanahit.entities.Subject;
import com.example.diplomaanahit.repositories.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SubjectDataService {
    @Autowired
    private SubjectRepository repository;

    public List<Subject> findAllSubjectsByStudentGroup(StudentGroup studentGroup) {
        return repository.findAllByStudentGroup(studentGroup.getId());
    }

    public List<SubjectDTO> toDTOList(List<Subject> list) {
        List<SubjectDTO> subjectDTOS = new ArrayList<>();
        for(Subject subject : list) {
            SubjectDTO subjectDTO = new SubjectDTO();
            subjectDTO.setName(subject.getName());
            subjectDTO.setId(subject.getId());
            subjectDTOS.add(subjectDTO);
       }
        return subjectDTOS;
    }

    public Subject findById(Long subjectId) {
        return repository.findById(subjectId).get();
    }

    public Set<Subject> findAll() {
        return repository.findAll().stream().collect(Collectors.toSet());
    }
}
