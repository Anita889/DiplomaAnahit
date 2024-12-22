package com.example.diplomaanahit.repositories;

import com.example.diplomaanahit.entities.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface LessonRepository extends JpaRepository<Lesson,Integer> {
    Optional<Lesson> findById(Integer id);

    void removeById(Integer id);
}
