package com.example.diplomaanahit.repositories;

import com.example.diplomaanahit.entities.LessonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface LessonRepository extends JpaRepository<LessonEntity, Integer> {
    Optional<LessonEntity> findById(Integer id);

    void removeById(Integer id);
}
