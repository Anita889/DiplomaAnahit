package com.example.diplomaanahit.repositories;

import com.example.diplomaanahit.entities.RegistrationType;
import com.example.diplomaanahit.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<UserEntity,Integer> {
    UserEntity findByEmailAndRegistrationType(String email, RegistrationType registered);

    UserEntity findByPassword(String password);
}
