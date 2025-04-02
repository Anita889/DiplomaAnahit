package com.example.diplomaanahit.dtos;

import com.example.diplomaanahit.entities.Subject;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LessonDTO implements Serializable {
    private Long id;
    private String type;

    private Boolean isAvailableDate;

    private LocalDate availableDate;
    private SubjectDTO subject;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDate getAvailableDate() {
        return availableDate;
    }

    public void setAvailableDate(LocalDate availableDate) {
        this.availableDate = availableDate;
    }

    public Boolean getIsAvailableDate() {
        return isAvailableDate;
    }

    public void setIsAvailableDate(Boolean isAvailableDate) {
        this.isAvailableDate = isAvailableDate;
    }
    public SubjectDTO getSubject() {
        return subject;
    }

    public void setSubject(SubjectDTO subject) {
        this.subject = subject;
    }
}
