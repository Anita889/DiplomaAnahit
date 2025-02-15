package com.example.diplomaanahit.services;


import com.example.diplomaanahit.dtos.QuestionsAnswerDTO;
import com.example.diplomaanahit.entities.AssessmentType;
import com.example.diplomaanahit.entities.Grade;
import com.example.diplomaanahit.entities.Lesson;
import com.example.diplomaanahit.entities.QuestionVariantsEntity;
import com.example.diplomaanahit.entities.Student;
import com.example.diplomaanahit.repositories.QuestionVariantsRepository;
import com.example.diplomaanahit.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
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

