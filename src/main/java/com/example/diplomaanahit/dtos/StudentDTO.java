package com.example.diplomaanahit.dtos;

import com.example.diplomaanahit.entities.Student;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link Student}
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Component
@NoArgsConstructor
@AllArgsConstructor
public class StudentDTO implements Serializable {
    private Long id;
    private String studentName;
    private String studentSurname;
    private LocalDate studentBirthDate;
    private Double mog;
    private String studentCity;
    private Integer academyGroupId;
    private String email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentSurname() {
        return studentSurname;
    }

    public void setStudentSurname(String studentSurname) {
        this.studentSurname = studentSurname;
    }

    public LocalDate getStudentBirthDate() {
        return studentBirthDate;
    }

    public void setStudentBirthDate(LocalDate studentBirthDate) {
        this.studentBirthDate = studentBirthDate;
    }

    public Double getMog() {
        return mog;
    }

    public void setMog(Double mog) {
        this.mog = mog;
    }

    public String getStudentCity() {
        return studentCity;
    }

    public void setStudentCity(String studentCity) {
        this.studentCity = studentCity;
    }

    public Integer getAcademyGroupId() {
        return academyGroupId;
    }

    public void setAcademyGroupId(Integer academyGroupId) {
        this.academyGroupId = academyGroupId;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

