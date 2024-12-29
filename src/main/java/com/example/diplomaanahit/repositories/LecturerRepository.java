package com.example.diplomaanahit.repositories;

import com.example.diplomaanahit.entities.LecturerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LecturerRepository extends JpaRepository<LecturerEntity, Integer> {
    Optional<LecturerEntity> findById(Integer id);

    void removeById(Integer id);
}
