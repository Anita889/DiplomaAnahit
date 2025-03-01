package com.example.diplomaanahit.services;


import com.example.diplomaanahit.entities.Student;
import com.example.diplomaanahit.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentDataService {

    @Autowired
    private StudentRepository repository;

    public Student findById(Long studentId) {
        Optional<Student> op = repository.findById(studentId);
        return op.orElse(null);
    }

    public Student findByEmail(String email) {
        return repository.findByEmail(email);
    }
}

