package com.example.diplomaanahit.repositories;

import com.example.diplomaanahit.entities.AcademyGroupsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AcademyGroupRepository extends JpaRepository<AcademyGroupsEntity, Integer> {
    Optional<AcademyGroupsEntity> findById(Integer id);

    void removeById(Integer id);
}
