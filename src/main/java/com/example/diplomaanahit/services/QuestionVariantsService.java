package com.example.diplomaanahit.services;


import com.example.diplomaanahit.entities.QuestionVariantsEntity;
import com.example.diplomaanahit.repositories.QuestionVariantsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionVariantsService {

   @Autowired
   QuestionVariantsRepository repository;

    public List<QuestionVariantsEntity> findQuestionVariantsListByLessonId(Integer id) {
        return repository.findQuestionVariantsListByLessonId(id);
    }
}
