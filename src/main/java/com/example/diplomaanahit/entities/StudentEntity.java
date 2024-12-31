package com.example.diplomaanahit.entities;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Entity
public class StudentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer  id;

    @Column(name = "name")
    private String studentName;

    @Column(name = "sur_name")
    private String studentSurname;

    @Column(name = "birthDate")
    private LocalDate studentBirthDate;

    @Column(name = "MOG")
    private Double mog;

    @Column(name = "city")
    private String studentCity;

    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "academy_group_id")
    private AcademyGroupsEntity academyGroup;

    public Integer getId() {
        return id;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getStudentSurname() {
        return studentSurname;
    }

    public LocalDate getStudentBirthDate() {
        return studentBirthDate;
    }

    public Double getMog() {
        return mog;
    }

    public String getStudentCity() {
        return studentCity;
    }

    public AcademyGroupsEntity getAcademyGroup() {
        return academyGroup;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public void setStudentSurname(String studentSurname) {
        this.studentSurname = studentSurname;
    }

    public void setStudentBirthDate(LocalDate studentBirthDate) {
        this.studentBirthDate = studentBirthDate;
    }

    public void setMog(Double mog) {
        this.mog = mog;
    }

    public void setStudentCity(String studentCity) {
        this.studentCity = studentCity;
    }

    public void setAcademyGroup(AcademyGroupsEntity academyGroup) {
        this.academyGroup = academyGroup;
    }
}
