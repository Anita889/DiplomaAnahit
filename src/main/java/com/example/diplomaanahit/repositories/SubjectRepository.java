package com.example.diplomaanahit.repositories;


import com.example.diplomaanahit.entities.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {


    @Query(nativeQuery = true, value = "SELECT s.name AS subject_name\n" +
            "FROM student_group sg\n" +
            "JOIN speciality sp ON sg.speciality_id = sp.id\n" +
            "JOIN department d ON sp.department_id = d.id\n" +
            "JOIN faculty f ON d.faculty_id = f.id\n" +
            "JOIN lesson l ON l.subject_id = sp.id \n" +
            "JOIN subject s ON l.subject_id = s.id\n" +
            "WHERE sg.id = :studentGroupId")
    List<Subject> findAllByStudentGroup(@Param("studentGroupId") Long studentGroupId);
}
