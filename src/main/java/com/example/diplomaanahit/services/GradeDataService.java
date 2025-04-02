package com.example.diplomaanahit.services;


import com.example.diplomaanahit.entities.Grade;
import com.example.diplomaanahit.repositories.GradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GradeDataService {

    @Autowired
    private GradeRepository repository;

    public void save(Grade grade){
        repository.save(grade);
    }
}
