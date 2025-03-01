package com.example.diplomaanahit.services;


import com.example.diplomaanahit.entities.Faculty;
import com.example.diplomaanahit.entities.Lecturer;
import com.example.diplomaanahit.repositories.FacultyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacultyDataService {


    @Autowired
    private FacultyRepository facultyRepository;

    public List<Faculty> findAll() {
        return facultyRepository.findAll();
    }

    public Faculty findById(Long facultyId) {
        return facultyRepository.findById(facultyId).orElse(null);
    }
}
