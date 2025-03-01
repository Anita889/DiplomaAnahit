package com.example.diplomaanahit.services;


import com.example.diplomaanahit.entities.Admin;
import com.example.diplomaanahit.repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminDataService {
    @Autowired
    private AdminRepository adminRepository;

    public Admin findById(Long adminId) {
        return adminRepository.findById(adminId).get();
    }
}
