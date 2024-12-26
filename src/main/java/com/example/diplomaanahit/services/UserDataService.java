package com.example.diplomaanahit.services;


import com.example.diplomaanahit.entities.RegistrationType;
import com.example.diplomaanahit.entities.UserEntity;
import com.example.diplomaanahit.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserDataService {

    @Autowired
    private UserRepository userRepository;

    public UserEntity findByEmailAndRegistrationType(String email, RegistrationType registered) {
        return userRepository.findByEmailAndRegistrationType(email, registered);
    }

    public UserEntity findByKey(String password) {
        return userRepository.findByPassword(password);
    }

    public UserEntity save(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }
}
