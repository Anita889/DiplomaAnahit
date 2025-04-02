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
    private Long id;

    private Long userId;
    private String email;
    private String password;
    private String registrationType;
    private LocalDate loginDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

}