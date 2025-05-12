package com.example.diplomaanahit.services;


import com.example.diplomaanahit.entities.*;
import com.example.diplomaanahit.repositories.ExamPointsRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExamPointsService {
    @Autowired
    private ExamPointsRepository examPointsRepository;

    public void saveAll(List<ExamPoints> examPoints) {
        examPointsRepository.saveAll(examPoints);
    }

    public List<ExamPoints> findBySubjectAndLecturer(Subject subject, Lecturer lecturer) {
        return examPointsRepository.findBySubjectAndLecturer(subject.getId(), lecturer.getId());
    }

    public List<ExamPoints> findAllByStudent(Student student) {
        return examPointsRepository.findAllByStudent(student.getId());
    }
}
