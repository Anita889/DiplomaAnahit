package com.example.diplomaanahit.repositories;

import com.example.diplomaanahit.entities.*;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamPointsRepository extends JpaRepository<ExamPoints, Long> {
    Optional<ExamPoints> findById(Long id);


    @Query(nativeQuery = true, value = "select*from exam_points where subject_id=:subjectId and lecturer_id=:lecturerId")
    List<ExamPoints> findBySubjectAndLecturer(@Param("subjectId") Long subjectId, @Param("lecturerId") Long lecturerId);

    @Query(nativeQuery = true, value = "select*from exam_points where student_id=:id")
    List<ExamPoints> findAllByStudent(@Param("id") Long id);
}