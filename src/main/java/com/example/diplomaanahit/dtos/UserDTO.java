package com.example.diplomaanahit.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link com.example.diplomaanahit.entities.UserEntity}
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Component
public class UserDTO implements Serializable {
    private Integer id;
    private String email;
    private String password;
    private String registrationType;
    private LocalDate loginDate;

    private StudentDTO student;

    private LecturerDTO lecturer;

    public Integer getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRegistrationType() {
        return registrationType;
    }

    public LocalDate getLoginDate() {
        return loginDate;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRegistrationType(String registrationType) {
        this.registrationType = registrationType;
    }

    public void setLoginDate(LocalDate loginDate) {
        this.loginDate = loginDate;
    }

    public StudentDTO getStudentDTO() {
        return student;
    }

    public LecturerDTO getLecturerDTO() {
        return lecturer;
    }

    public void setStudentDTO(StudentDTO student) {
        this.student = student;
    }

    public void setLecturerDTO(LecturerDTO lecturer) {
        this.lecturer = lecturer;
    }
}