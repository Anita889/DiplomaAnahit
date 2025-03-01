package com.example.diplomaanahit.services;


import com.example.diplomaanahit.entities.StudentGroup;
import com.example.diplomaanahit.repositories.StudentGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentGroupDataService {
    @Autowired
    private StudentGroupRepository studentGroupRepository;

    public StudentGroup findById(Long studentGroupId) {
        return studentGroupRepository.findById(studentGroupId).get();
    }

    public void saveStudentGroup(StudentGroup studentGroup) {
        studentGroupRepository.save(studentGroup);
    }

    public void deleteStudentGroup(StudentGroup studentGroup) {
       studentGroupRepository.delete(studentGroup);
    }
}
