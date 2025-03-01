package com.example.diplomaanahit.dtos;

import com.example.diplomaanahit.entities.Subject;
import java.time.LocalDate;

public class LessonDTO {
    private Long id;
    private String type;

    private LocalDate availableDate;
    private Subject subject;

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

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }
}
