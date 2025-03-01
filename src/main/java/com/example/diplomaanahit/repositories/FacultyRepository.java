package com.example.diplomaanahit.repositories;

import com.example.diplomaanahit.entities.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FacultyRepository extends JpaRepository<Faculty, Long> {

}
