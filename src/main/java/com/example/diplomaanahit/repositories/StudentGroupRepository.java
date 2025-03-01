package com.example.diplomaanahit.repositories;

import com.example.diplomaanahit.entities.StudentGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentGroupRepository extends JpaRepository<StudentGroup, Long> {
    Optional<StudentGroup> findById(Long id);

    void removeById(Integer id);
}
