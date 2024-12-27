package com.example.diplomaanahit.services;


import com.example.diplomaanahit.entities.UserEntity;
import com.example.diplomaanahit.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDataService {

    @Autowired
    private UserRepository userRepository;


    public UserEntity findByKey(String password, String email) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return userRepository.findByPasswordAndEmail(encoder.encode(password), email);
    }

    public UserEntity save(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }

    public UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
