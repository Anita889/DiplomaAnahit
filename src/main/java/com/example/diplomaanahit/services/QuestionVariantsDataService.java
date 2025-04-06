package com.example.diplomaanahit.services;


import com.example.diplomaanahit.dtos.QuestionVariantsLecturerDTO;
import com.example.diplomaanahit.dtos.QuestionVariantsStudentDTO;
import com.example.diplomaanahit.entities.Lesson;
import com.example.diplomaanahit.entities.QuestionVariantsEntity;
import com.example.diplomaanahit.mapper.Mapper;
import com.example.diplomaanahit.repositories.QuestionVariantsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionVariantsDataService {

   @Autowired
   private QuestionVariantsRepository repository;

   @Autowired
   private Mapper mapper;

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

    public List<QuestionVariantsEntity> toEntityList(List<QuestionVariantsLecturerDTO> questions, Lesson lesson) {
        return mapper.getQuestionVariantsEntityList(questions, lesson);
    }

    public void saveAll(List<QuestionVariantsEntity> list) {
        repository.saveAll(list);
    }

    public QuestionVariantsEntity findById(Long questionId) {
        return repository.findById(questionId).orElse(null);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public void save(QuestionVariantsEntity questionVariantsEntity) {
        repository.save(questionVariantsEntity);
    }

    public List<QuestionVariantsLecturerDTO> toQuestionVariantsLecturerDTOList(List<QuestionVariantsEntity> list) {
        return mapper.getQuestionVariantsLecturerDTOList(list);
    }
    public QuestionVariantsLecturerDTO toDTO(QuestionVariantsEntity questionVariantsEntity) {
        return mapper.getQuestionVariantsLecturerDTO(questionVariantsEntity);
    }

    public List<QuestionVariantsEntity> findQuestionVariantsListByLessonName(String type) {
        return repository.findQuestionVariantsListByLessonName(type);
    }
}
