package com.example.diplomaanahit.services;


import com.example.diplomaanahit.entities.StudentEntity;
import com.example.diplomaanahit.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentService {
    @Autowired
    private StudentRepository repository;


    public StudentEntity findById(Integer studentId) {
        Optional<StudentEntity> op = repository.findById(studentId);
        return op.orElse(null);
    }

    public StudentEntity findByEmail(String email) {
        return repository.findByEmail(email);
    }
}

