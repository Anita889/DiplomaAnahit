package com.example.diplomaanahit.repositories;

import com.example.diplomaanahit.entities.Lecturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LecturerRepository extends JpaRepository<Lecturer, Long> {
    Optional<Lecturer> findById(Long id);

    void removeById(Integer id);

    Lecturer findByEmail(String email);
}
