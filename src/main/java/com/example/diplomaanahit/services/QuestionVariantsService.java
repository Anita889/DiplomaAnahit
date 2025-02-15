package com.example.diplomaanahit.services;


import com.example.diplomaanahit.dtos.QuestionVariantsStudentDTO;
import com.example.diplomaanahit.dtos.QuestionAndVariantsForLecturerDTO;
import com.example.diplomaanahit.entities.Lesson;
import com.example.diplomaanahit.entities.QuestionVariantsEntity;
import com.example.diplomaanahit.mapper.QuestionVariantsMapper;
import com.example.diplomaanahit.repositories.QuestionVariantsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionVariantsService {

   @Autowired
   private QuestionVariantsRepository repository;

   @Autowired
   private QuestionVariantsMapper mapper;

    public List<QuestionVariantsEntity> findQuestionVariantsListByLessonId(Long id) {
        return repository.findQuestionVariantsListByLessonId(id);
    }

    public List<QuestionVariantsStudentDTO> getQuestionVariantsDTOList(Long lessonId) {
        List<QuestionVariantsEntity> list = findQuestionVariantsListByLessonId(lessonId);
        return mapper.getQuestionVariantsDTOList(list, false);
    }

    public List<QuestionVariantsStudentDTO> toDTOList(List<QuestionVariantsEntity> list) {
        return mapper.getQuestionVariantsDTOList(list, false);
    }

    public List<QuestionVariantsEntity> toEntityList(List<QuestionAndVariantsForLecturerDTO> questions, Lesson lesson) {
        return mapper.getQuestionVariantsEntityList(questions, lesson);
    }

    public void saveAll(List<QuestionVariantsEntity> list) {
        repository.saveAll(list);
    }
}
