package com.example.diplomaanahit.repositories;


import com.example.diplomaanahit.entities.QuestionVariantsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionVariantsRepository extends JpaRepository<QuestionVariantsEntity, Integer> {
    List<QuestionVariantsEntity> findQuestionVariantsListByLessonId(Integer lessonId);
}
