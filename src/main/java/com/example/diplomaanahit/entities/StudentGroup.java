package com.example.diplomaanahit.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Entity
public class StudentGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "speciality_id")
    private Speciality speciality;

    @OneToMany(mappedBy = "studentGroup")
    private Set<Student> students;


    @ManyToMany
    @JoinTable(
            name = "student_group_subject",  // Join table to represent many-to-many relationship
            joinColumns = @JoinColumn(name = "student_group_id"),  // Foreign key for the student
            inverseJoinColumns = @JoinColumn(name = "subject_id")  // Foreign key for the subject
    )
    private Set<Subject> subjects;
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Speciality getSpeciality() {
        return speciality;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSpeciality(Speciality speciality) {
        this.speciality = speciality;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }
}