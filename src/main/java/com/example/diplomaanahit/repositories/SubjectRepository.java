package com.example.diplomaanahit.repositories;


import com.example.diplomaanahit.entities.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {


    @Query(nativeQuery = true, value = "select * from subject where id in " +
            "(select subject_id from student_group_subject where student_group_id=:studentGroupId)")
    List<Subject> findAllByStudentGroup(@Param("studentGroupId") Long studentGroupId);
}
