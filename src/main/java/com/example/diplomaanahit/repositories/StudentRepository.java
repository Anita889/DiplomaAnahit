package com.example.diplomaanahit.repositories;

import com.example.diplomaanahit.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findById(Long id);

    Student findByEmail(String email);
}
