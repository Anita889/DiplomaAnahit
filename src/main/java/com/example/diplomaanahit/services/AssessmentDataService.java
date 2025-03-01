package com.example.diplomaanahit.services;


import com.example.diplomaanahit.entities.AssessmentType;
import com.example.diplomaanahit.repositories.AssessmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AssessmentDataService {

    @Autowired
    private AssessmentRepository repository;

    public AssessmentType findByAssessmentType(String assessmentType) {
        return repository.findByName(assessmentType);
    }
}
