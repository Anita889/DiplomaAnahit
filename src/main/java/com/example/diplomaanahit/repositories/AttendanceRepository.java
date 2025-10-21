package com.example.diplomaanahit.repositories;

import com.example.diplomaanahit.entities.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface  AttendanceRepository extends JpaRepository<Attendance, Long> {
    // Custom query methods can be defined here if needed
    // For example, you can add methods to find attendance records by student ID or date
}
