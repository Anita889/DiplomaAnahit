package com.example.diplomaanahit.services;


import com.example.diplomaanahit.dtos.QuestionVariantsDTO;
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

    public List<QuestionVariantsEntity> findQuestionVariantsListByLessonId(Integer id) {
        return repository.findQuestionVariantsListByLessonId(id);
    }

    public List<QuestionVariantsDTO> getQuestionVariantsDTOList(int lessonId) {
        List<QuestionVariantsEntity> list = findQuestionVariantsListByLessonId(lessonId);
        return mapper.getQuestionVariantsDTOList(list, false);
    }
}
