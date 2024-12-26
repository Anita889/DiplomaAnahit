package com.example.diplomaanahit.dtos;


import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.stereotype.Component;


@JsonInclude(JsonInclude.Include.NON_NULL)
@Component
public class AuthDTO {

    private Integer id;

    private String email;

    private String password;
    public Integer getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
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
}
