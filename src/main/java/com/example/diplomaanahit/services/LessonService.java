package com.example.diplomaanahit.services;


import com.example.diplomaanahit.entities.LessonEntity;
import com.example.diplomaanahit.repositories.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LessonService {
    @Autowired
    private LessonRepository repository;

    public LessonEntity findById(Integer id) {
        Optional<LessonEntity> op = repository.findById(id);
        return op.orElse(null);
    }
}
