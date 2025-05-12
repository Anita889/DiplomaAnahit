package com.example.diplomaanahit.services;


import com.example.diplomaanahit.dtos.LessonDTO;
import com.example.diplomaanahit.dtos.LessonSimpleDTO;
import com.example.diplomaanahit.entities.*;
import com.example.diplomaanahit.repositories.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class LessonDataService {
    @Autowired
    private LessonRepository repository;

    public Lesson findById(Long id) {
        Optional<Lesson> op = repository.findById(id);
        return op.orElse(null);
    }



    public List<Lesson> findAllByStudentGroupAndSubject(Long studentGroupId, Subject subject) {
       return repository.findAllByAcademyGroup(studentGroupId, subject.getId());
    }

    public List<LessonDTO> toDTOList(List<Lesson> list) {
        List<LessonDTO> dtoList = new ArrayList<>();
        for(Lesson entity : list){
           dtoList.add(toDTO(entity));
        }
        return dtoList;
    }

    public LessonDTO toDTO(Lesson entity) {
        LessonDTO dto = new LessonDTO();
        dto.setId(entity.getId());
        dto.setType(entity.getType());
        dto.setIsAvailableDate(entity.getAvailableDate().isBefore(LocalDate.now()));
        return dto;
    }

    public List<Lesson> findBySubject(Subject subject) {
        return repository.findBySubject(subject);
    }

    public void save(Lesson lesson) {
        repository.save(lesson);
    }

    public List<Long> findByLessonName(String type) {
        return repository.findByLessonName(type);
    }

    public List<LessonSimpleDTO> toSimpleDTOList(List<Lesson> list) {
        Map<String, LessonSimpleDTO> map = new HashMap<>();
        for(Lesson entity : list){
            LessonSimpleDTO dto = new LessonSimpleDTO();
            dto.setId(entity.getId());
            dto.setType(entity.getType());
            map.put(entity.getType(), dto);
        }
        return map.values().stream().toList();
    }

    public List<Lesson> findBySubjectAndLecturer(Subject subject, Lecturer lecturer) {
        return repository.findBySubjectAndLecturer(subject.getId(), lecturer.getId());
    }

    public List<Lesson> findAllByStudentGroups(List<StudentGroup> studentGroups) {
        List<Lesson> lessons = new ArrayList<>();
        for (StudentGroup studentGroup : studentGroups) {
            List<Lesson> groupLessons = repository.findAllByAcademyGroup(studentGroup.getId(), null);
            lessons.addAll(groupLessons);
        }
        return lessons;
    }
}
