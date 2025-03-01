package com.example.diplomaanahit.dtos;

import java.util.Set;

public class StudentGroupDTO {
    private Long id;
    private String name;
    private SpecialityDTO speciality;

    private Set<StudentDTO> students;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SpecialityDTO getSpeciality() {
        return speciality;
    }

    public void setSpeciality(SpecialityDTO speciality) {
        this.speciality = speciality;
    }

    public Set<StudentDTO> getStudents() {
        return students;
    }

    public void setStudents(Set<StudentDTO> students) {
        this.students = students;
    }
}
