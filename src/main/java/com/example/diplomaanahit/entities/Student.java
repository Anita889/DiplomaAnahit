package com.example.diplomaanahit.entities;


import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;

    @ManyToOne
    @JoinColumn(name = "student_group_id")
    private StudentGroup studentGroup;

    @OneToMany(mappedBy = "student")
    private Set<Attendance> attendances;

    @OneToMany(mappedBy = "student")
    private Set<Grade> grades;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public StudentGroup getStudentGroup() {
        return studentGroup;
    }

    public Set<Attendance> getAttendances() {
        return attendances;
    }

    public Set<Grade> getGrades() {
        return grades;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStudentGroup(StudentGroup studentGroup) {
        this.studentGroup = studentGroup;
    }

    public void setAttendances(Set<Attendance> attendances) {
        this.attendances = attendances;
    }

    public void setGrades(Set<Grade> grades) {
        this.grades = grades;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
