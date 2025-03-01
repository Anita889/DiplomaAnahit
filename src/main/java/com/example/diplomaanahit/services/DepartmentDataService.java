package com.example.diplomaanahit.services;


import com.example.diplomaanahit.entities.Department;
import com.example.diplomaanahit.repositories.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartmentDataService {

    @Autowired
    private DepartmentRepository departmentRepository;

    public Department findById(Long departmentId) {
        return departmentRepository.findById(departmentId).get();
    }
}
