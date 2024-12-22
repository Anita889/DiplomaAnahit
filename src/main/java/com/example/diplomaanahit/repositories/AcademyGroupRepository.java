package com.example.diplomaanahit.repositories;

import com.example.diplomaanahit.entities.AcademyGroups;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AcademyGroupRepository extends JpaRepository<AcademyGroups,Integer> {
    Optional<AcademyGroups> findById(Integer id);

    void removeById(Integer id);
}
