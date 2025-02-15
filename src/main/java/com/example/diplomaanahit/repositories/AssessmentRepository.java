package com.example.diplomaanahit.repositories;


import com.example.diplomaanahit.entities.AssessmentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssessmentRepository extends JpaRepository<AssessmentType, Long> {
    AssessmentType findByAssessmentType(String assessmentType);
}
