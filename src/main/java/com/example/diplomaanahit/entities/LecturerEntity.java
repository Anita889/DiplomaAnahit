package com.example.diplomaanahit.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@NoArgsConstructor
@AllArgsConstructor
@Entity
public class LecturerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer  id;

    @Column(name = "name")
    private String lecturerName;

    @Column(name = "sur_name")
    private String lecturerSurname;

    @Column(name = "birthDate")
    private LocalDate lecturerBirthDate;

    @Column(name = "criteria")
    private Double criteria;

    @Column(name = "city")
    private String lecturerCity;

    @Column(name = "subject")
    private String subject;

    @Column(name = "email")
    private String email;

    public Integer getId() {
        return id;
    }

    public String getLecturerName() {
        return lecturerName;
    }

    public String getLecturerSurname() {
        return lecturerSurname;
    }

    public LocalDate getLecturerBirthDate() {
        return lecturerBirthDate;
    }

    public Double getCriteria() {
        return criteria;
    }

    public String getLecturerCity() {
        return lecturerCity;
    }

    public String getSubject() {
        return subject;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setLecturerName(String lecturerName) {
        this.lecturerName = lecturerName;
    }

    public void setLecturerSurname(String lecturerSurname) {
        this.lecturerSurname = lecturerSurname;
    }

    public void setLecturerBirthDate(LocalDate lecturerBirthDate) {
        this.lecturerBirthDate = lecturerBirthDate;
    }

    public void setCriteria(Double criteria) {
        this.criteria = criteria;
    }

    public void setLecturerCity(String lecturerCity) {
        this.lecturerCity = lecturerCity;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
