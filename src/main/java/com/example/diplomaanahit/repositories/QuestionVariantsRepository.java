package com.example.diplomaanahit.repositories;


import com.example.diplomaanahit.entities.QuestionVariantsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionVariantsRepository extends JpaRepository<QuestionVariantsEntity, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM question_variants_entity WHERE lesson_id = :lessonId")
    List<QuestionVariantsEntity> findQuestionVariantsListByLessonId(@Param("lessonId") Long lessonId);
}
