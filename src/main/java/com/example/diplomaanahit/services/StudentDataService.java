package com.example.diplomaanahit.services;


import com.example.diplomaanahit.entities.Student;
import com.example.diplomaanahit.repositories.StudentRepository;
import com.example.diplomaanahit.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class StudentDataService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private UserRepository userRepository;

    public Student findById(Long studentId) {
        Optional<Student> op = studentRepository.findById(studentId);
        return op.orElse(null);
    }

    public Student findByEmail(String email) {
        return studentRepository.findByEmail(email);
    }

    public void save(Student student) {
        studentRepository.save(student);
    }

    public void remove(Student student) {
        userRepository.deleteById(Objects.requireNonNull(userRepository.findByStudentId(student.getId()).getId()));
        studentRepository.deleteById(student.getId());
    }

}

