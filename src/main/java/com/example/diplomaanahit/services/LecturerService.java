package com.example.diplomaanahit.services;


import com.example.diplomaanahit.entities.LecturerEntity;
import com.example.diplomaanahit.repositories.LecturerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LecturerService {
    @Autowired
    private LecturerRepository lecturerRepository;

    public LecturerEntity findById(int lecturerId) {
        return lecturerRepository.findById(lecturerId).get();
    }

    public LecturerEntity findByEmail(String email) {
        return  lecturerRepository.findByEmail(email);
    }
}
