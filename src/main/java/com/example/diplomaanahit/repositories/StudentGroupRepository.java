package com.example.diplomaanahit.repositories;

import com.example.diplomaanahit.entities.Lesson;
import com.example.diplomaanahit.entities.StudentGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentGroupRepository extends JpaRepository<StudentGroup, Long> {
    Optional<StudentGroup> findById(Long id);

    void removeById(Integer id);

    List<StudentGroup> findByLessons(Lesson lesson);


    @Query(nativeQuery = true, value = "select*from student_group where speciality_id in (select id from speciality where department_id=:id)")
    List<StudentGroup> findAllByDepartment(@Param("id") Long id);
}
