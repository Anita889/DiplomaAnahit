package com.example.diplomaanahit.repositories;

import com.example.diplomaanahit.entities.Department;
import com.example.diplomaanahit.entities.Lecturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface LecturerRepository extends JpaRepository<Lecturer, Long> {
    Optional<Lecturer> findById(Long id);

    void removeById(Integer id);

    Lecturer findByEmail(String email);


    @Query(nativeQuery = true, value = "SELECT * FROM lecturer  WHERE department_id=:id")
    List<Lecturer> findByDepartment(@Param("id") Long id);
}
