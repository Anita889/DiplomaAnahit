package com.example.diplomaanahit.repositories;

import com.example.diplomaanahit.entities.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, Integer> {
    Optional<StudentEntity> findById(Integer id);

    void removeById(Integer id);
}
