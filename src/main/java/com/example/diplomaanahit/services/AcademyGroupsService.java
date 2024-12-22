package com.example.diplomaanahit.services;


import com.example.diplomaanahit.repositories.AcademyGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AcademyGroupsService {
    @Autowired
    private AcademyGroupRepository academyGroupRepository;
}
