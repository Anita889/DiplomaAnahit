package com.example.diplomaanahit.services;


import com.example.diplomaanahit.entities.Attendance;
import com.example.diplomaanahit.repositories.AttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class AttendanceDataService {

    @Autowired
    private AttendanceRepository repository;


    public void save(Attendance attendance) {
        repository.save(attendance);
    }
}
