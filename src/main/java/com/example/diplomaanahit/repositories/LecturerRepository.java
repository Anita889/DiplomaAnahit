package com.example.diplomaanahit.repositories;

import com.example.diplomaanahit.entities.Lecturer;
import com.example.diplomaanahit.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LecturerRepository extends JpaRepository<Lecturer,Integer> {
    Optional<Lecturer> findById(Integer id);

    void removeById(Integer id);
}
