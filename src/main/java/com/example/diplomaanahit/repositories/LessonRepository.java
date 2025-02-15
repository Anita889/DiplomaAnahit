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

    @Query(nativeQuery = true, value = "select*from lesson where subject_id=:id")
    List<Lesson> findAllByAcademyGroup(@Param("id") Long id);

    List<Lesson> findBySubject(Subject subject);
}
