package com.example.diplomaanahit.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Component
@NoArgsConstructor
@AllArgsConstructor
public class LecturerDTO implements Serializable {
    private Integer id;
    private String lecturerName;
    private String lecturerSurname;
    private LocalDate lecturerBirthDate;
    private Double criteria;
    private String lecturerCity;
    private String subject;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLecturerName() {
        return lecturerName;
    }

    public void setLecturerName(String lecturerName) {
        this.lecturerName = lecturerName;
    }

    public String getLecturerSurname() {
        return lecturerSurname;
    }

    public void setLecturerSurname(String lecturerSurname) {
        this.lecturerSurname = lecturerSurname;
    }

    public LocalDate getLecturerBirthDate() {
        return lecturerBirthDate;
    }

    public void setLecturerBirthDate(LocalDate lecturerBirthDate) {
        this.lecturerBirthDate = lecturerBirthDate;
    }

    public Double getCriteria() {
        return criteria;
    }

    public void setCriteria(Double criteria) {
        this.criteria = criteria;
    }

    public String getLecturerCity() {
        return lecturerCity;
    }

    public void setLecturerCity(String lecturerCity) {
        this.lecturerCity = lecturerCity;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
