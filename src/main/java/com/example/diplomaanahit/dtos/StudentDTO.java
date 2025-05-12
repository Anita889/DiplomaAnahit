package com.example.diplomaanahit.dtos;

import com.example.diplomaanahit.entities.Student;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link Student}
 */
@JsonInclude(JsonInclude.Include.NON_NULL) // Exclude null fields from the response
@NoArgsConstructor
@AllArgsConstructor
public class StudentDTO implements Serializable {

    private Long id;
    private String studentName;
    private String studentSurname;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate studentBirthDate;
    private Double mog;
    private Double score;
    private Integer point;
    private String studentCity;
    private Long academyGroupId;
    private String email;
    private StudentGroupDTO studentGroupDTO;
    private String password;
    private String lecturerInfo;

    boolean isPresent;

    public StudentGroupDTO getStudentGroupDTO() {
        return studentGroupDTO;
    }

    public void setStudentGroupDTO(StudentGroupDTO studentGroupDTO) {
        this.studentGroupDTO = studentGroupDTO;
    }

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

    public Long getAcademyGroupId() {
        return academyGroupId;
    }

    public void setAcademyGroupId(Long academyGroupId) {
        this.academyGroupId = academyGroupId;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public void setLecturerInfo(String lecturerInfo) {
        this.lecturerInfo = lecturerInfo;
    }

    public String getLecturerInfo() {
        return lecturerInfo;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public boolean isPresent() {
        return isPresent;
    }

    public void setPresent(boolean present) {
        isPresent = present;
    }
}

