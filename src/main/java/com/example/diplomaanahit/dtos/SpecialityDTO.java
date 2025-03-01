package com.example.diplomaanahit.dtos;


import java.util.Set;

public class SpecialityDTO {
    private Long id;
    private String name;

    private DepartmentDTO department;

    private Set<StudentGroupDTO> studentGroups;

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

    public DepartmentDTO getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentDTO department) {
        this.department = department;
    }

    public Set<StudentGroupDTO> getStudentGroups() {
        return studentGroups;
    }

    public void setStudentGroups(Set<StudentGroupDTO> studentGroups) {
        this.studentGroups = studentGroups;
    }
}
