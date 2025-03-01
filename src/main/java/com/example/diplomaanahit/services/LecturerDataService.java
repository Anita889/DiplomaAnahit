package com.example.diplomaanahit.services;


import com.example.diplomaanahit.entities.Lecturer;
import com.example.diplomaanahit.repositories.LecturerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LecturerDataService {
    @Autowired
    private LecturerRepository lecturerRepository;

    public Lecturer findById(Long lecturerId) {
        return lecturerRepository.findById(lecturerId).get();
    }

    public Lecturer findByEmail(String email) {
        return  lecturerRepository.findByEmail(email);
    }

    public void saveLecturer(Lecturer lecturer) {
        lecturerRepository.save(lecturer);
    }

    public void deleteLecturer(Lecturer lecturer) {
        lecturerRepository.delete(lecturer);
    }
}
