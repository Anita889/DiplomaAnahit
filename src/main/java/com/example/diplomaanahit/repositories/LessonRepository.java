package com.example.diplomaanahit.repositories;

import com.example.diplomaanahit.entities.Lesson;
import com.example.diplomaanahit.entities.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface LessonRepository extends JpaRepository<Lesson, Integer> {
    Optional<Lesson> findById(Long id);

    @Query(nativeQuery = true, value = "select*from lesson where subject_id=:subjectId and student_group_id=:studentGroupId")
    List<Lesson> findAllByAcademyGroup(@Param("studentGroupId") Long studentGroupId, @Param("subjectId") Long subjectId);

    List<Lesson> findBySubject(Subject subject);

    @Query(nativeQuery = true, value = "select student_group_id from lesson where type=:type")
    List<Long> findByLessonName(@Param("type") String type);
}
