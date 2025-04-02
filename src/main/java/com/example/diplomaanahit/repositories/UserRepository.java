package com.example.diplomaanahit.repositories;

import com.example.diplomaanahit.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {

    UserEntity findByPasswordAndEmail(String password, String email);

    UserEntity findByEmail(String email);

    UserEntity findByStudentId(Long studentId);
}
